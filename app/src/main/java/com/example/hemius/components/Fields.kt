package com.example.hemius.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.hemius.ui.theme.HemiusColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HemiusTextField(
    modifier : Modifier = Modifier,
    value:String,
    onValueChange:(String) -> Unit,
    placeholder: @Composable() (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
){
    OutlinedTextField(
        modifier = modifier
            .background(HemiusColors.current.background)
//            .padding(19.5.dp)
        ,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = HemiusColors.current.blueFirst,
            unfocusedBorderColor = HemiusColors.current.lines
        ),
        textStyle = textStyle,
        value = value,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(26.dp),
        placeholder = placeholder
    )
}