package tw.edu.pu.csim.tcyang.race

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun GameScreen(message: String, gameViewModel: GameViewModel) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Yellow)
    ){
        Canvas (modifier = Modifier.fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    gameViewModel.MoveCircle( dragAmount.x, dragAmount.y)
                }
            }
        ) {
            // 繪製圓形
            drawCircle(
                color = Color.Red,
                radius = 100f,
                center = Offset(gameViewModel.circleX, gameViewModel.circleY)
            )
        }

        Column {
            // 顯示螢幕尺寸、姓名和分數
            Text(text = message + gameViewModel.screenWidthPx.toString() + "*"
                    + gameViewModel.screenHeightPx.toString() + " 姓名：魏郁倫 分數：${gameViewModel.score}")

            // 按鈕在文字下方
            Button(onClick = {
                gameViewModel.gameRunning = true
                gameViewModel.StartGame()
            }
            ){
                Text("遊戲開始")
            }
        }
    }
}