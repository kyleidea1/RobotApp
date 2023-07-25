package com.example.robotapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CellButton() {
    var value by remember { mutableStateOf("1") }
    var cellNum by remember { mutableStateOf("1") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(75.dp)
            .background(Color(0xFFD9D9D9), RoundedCornerShape(30.dp))
            .clickable {
                keyboardController?.hide() // hiding the keyboard
                focusManager.clearFocus() // clearing the focus from the TextField
            },
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("셀", Modifier.padding(end = 8.dp)) // Here is your text

            OutlinedTextField(
                value = value,
                onValueChange = { newValue ->
                    if (newValue.toIntOrNull() != null || newValue.isEmpty()) {
                        value = newValue
                        cellNum = newValue // saving the input value in cellNum variable
                    }
                },
                textStyle = TextStyle(fontSize = 17.sp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.NumberPassword,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide() // hiding keyboard on pressing the Check button
                        focusManager.clearFocus() // clearing the focus from the TextField
                        cellNum = value // saving the input value in cellNum variable
                    }
                ),
                modifier = Modifier.size(100.dp, 50.dp)
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxHeight()
            ) {
                IconButton(
                    onClick = {
                        if (value.toIntOrNull() != null) value = (value.toInt() + 1).toString()
                        cellNum = value // saving the updated value in cellNum variable
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(painterResource(R.drawable.upbtn), contentDescription = "Increase Value", modifier = Modifier.size(12.dp))
                }

                IconButton(
                    onClick = {
                        if (value.toIntOrNull() != null && value.toInt() > 0) value = (value.toInt() - 1).toString()
                        cellNum = value // saving the updated value in cellNum variable
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Icon(painterResource(R.drawable.downbtn), contentDescription = "Decrease Value", modifier = Modifier.size(12.dp))
                }
            }
        }
    }
}

@Composable
fun LeftBtnArea() {
    Column(
        Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    ) {
        val hosunImage = painterResource(R.drawable.hosunimage)
        val blockImage = painterResource(R.drawable.blockimage)
        val removeimage = painterResource(R.drawable.removeimage)
        val startimage = painterResource(R.drawable.startimage)
        val haltimage = painterResource(R.drawable.haltimage)

        createButton(image = hosunImage) {

        }
        createButton(image = blockImage) {

        }
        CellButton()
        createButton(image = removeimage) {

        }
        createButton(image = startimage) {

        }
        createButton(image = haltimage) {

        }
    }
}
