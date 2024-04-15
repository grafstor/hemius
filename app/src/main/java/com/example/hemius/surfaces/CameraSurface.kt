package com.example.hemius.surfaces

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color.toArgb
import android.graphics.Matrix
import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.hemius.R
import com.example.hemius.database.events.ThingEvent
import com.example.hemius.database.states.ThingState
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.segmentation.subject.SubjectSegmentation
import com.google.mlkit.vision.segmentation.subject.SubjectSegmenterOptions
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.math.max
import kotlin.math.min

@Composable
fun CameraSurface(
    applicationContext : Context,
    onBackClick : () -> Unit,
    onCaptureClick : () -> Unit,

    state: ThingState,
    onEvent: (ThingEvent) -> Unit,
) {
    val controller = remember {
        LifecycleCameraController(applicationContext).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE
            )
        }
    }
    var isProcessing by remember { mutableStateOf<Boolean>(false) }
    Surface (
        modifier = Modifier
        .fillMaxSize()
    ){
        CameraPreview(
            controller = controller,
            modifier = Modifier
                .fillMaxSize()
        )
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.BottomStart)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .height(119.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 18.dp, vertical = 0.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    val mainScope = MainScope()

                    Box(
                        modifier = Modifier
                            .height(87.dp)
                            .wrapContentHeight()
                            .clickable(onClick = {
                                mainScope.launch {
                                    isProcessing = true
                                    takePhoto(
                                        controller = controller,
                                        applicationContext = applicationContext,
                                        onPhotoTaken = { bitmap ->
                                            onEvent(ThingEvent.SetImage(bitmap))

                                            onCaptureClick()
                                            isProcessing = false
                                        }
                                    )
                                }
                            })
                            .align(
                                Alignment.CenterVertically
                            ),
                    ) {
                        RotatingIcon(isProcessing = isProcessing)
                    }
                }
            }
        }
    }
}

@Composable
fun RotatingIcon(isProcessing: Boolean) {
    val rotationAngle = remember { Animatable(0f) }

    LaunchedEffect(isProcessing) {
        if (isProcessing) {
            rotationAngle.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 300,
                        easing = LinearEasing
                    )
                )
            )
        } else {
            rotationAngle.stop()
        }
    }

    Box(
        modifier = Modifier.size(119.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Icon(
            modifier = Modifier
                .size(68.dp)
                .graphicsLayer(
                    rotationZ = rotationAngle.value
                ),
            painter = painterResource(id = R.drawable.ic_capture),
            tint = Color.White,
            contentDescription = "Photo Icon"
        )
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    onPhotoTaken: (Bitmap) -> Unit,
    applicationContext : Context
) {
    controller.takePicture(
        ContextCompat.getMainExecutor(applicationContext),
        object : ImageCapture.OnImageCapturedCallback() {
            @OptIn(ExperimentalGetImage::class) override fun onCaptureSuccess(imageProxy: ImageProxy) {
                super.onCaptureSuccess(imageProxy)

                val mediaImage = imageProxy.image

                if (mediaImage != null) {
                    val inputImage = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                    val options = SubjectSegmenterOptions.Builder()
                            .enableForegroundBitmap()
                        .build()
                    val segmenter = SubjectSegmentation.getClient(options)


                    segmenter.process(inputImage)
                        .addOnSuccessListener { result ->
                            var foregroundBitmap = result.foregroundBitmap!!

                            val scaleFactor = min(1.0, 1080.0 / max(inputImage.width, inputImage.height).toDouble())
                            var scaledBitmap = Bitmap.createScaledBitmap(foregroundBitmap, (inputImage.width * scaleFactor).toInt(), (inputImage.height * scaleFactor).toInt(), true)

                            scaledBitmap = cropBitmap(scaledBitmap)


                            onPhotoTaken(scaledBitmap)
                        }
                        .addOnFailureListener { e ->
                            // Task failed with an exception
                        }
                }

            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.e("Camera", "Couldn't take photo: ", exception)
            }
        }
    )
}

private fun cropBitmap(bitmap: Bitmap): Bitmap {
    var minX = bitmap.width
    var minY = bitmap.height
    var maxX = -1
    var maxY = -1

    for (x in 0 until bitmap.width) {
        for (y in 0 until bitmap.height) {
            if (bitmap.getPixel(x, y) != 0) {
                minX = min(minX, x)
                minY = min(minY, y)
                maxX = max(maxX, x)
                maxY = max(maxY, y)
            }
        }
    }

    return Bitmap.createBitmap(bitmap, minX, minY, maxX - minX + 1, maxY - minY + 1)
}

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        factory = {
            PreviewView(it).apply {
                this.controller = controller
                controller.bindToLifecycle(lifecycleOwner)
            }
        },
        modifier = modifier
    )
}