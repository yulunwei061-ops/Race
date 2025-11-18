package tw.edu.pu.csim.tcyang.race

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {

    var screenWidthPx by mutableStateOf(0f)
        private set

    var screenHeightPx by mutableStateOf(0f)
        private set

    var gameRunning by mutableStateOf(false)

    var circleX by mutableStateOf(0f)
    var circleY by mutableStateOf(0f)

    // 建立三匹馬的陣列
    val horses = arrayOf(Horse(), Horse(), Horse())

    // 新增分數變數
    var score by mutableStateOf(0)
        private set

    // 新增獲勝訊息
    var winnerText by mutableStateOf("")
        private set

    // 設定螢幕寬度與高度
    fun SetGameSize(w: Float, h: Float) {
        screenWidthPx = w
        screenHeightPx = h

        // 初始化三匹馬的位置(遊戲還沒開始就顯示)
        for (i in 0..2) {
            horses[i].horseX = 0
            horses[i].horseY = 100 + 300 * i
        }
    }

    fun StartGame() {
        //回到初使位置
        circleX = 100f
        circleY = screenHeightPx - 100f
        score = 0  // 重置分數
        winnerText = ""  // 清空獲勝訊息

        // 設定三匹馬的初始位置
        for (i in 0..2) {
            horses[i].horseX = 0
            horses[i].horseY = 100 + 300 * i
        }

        viewModelScope.launch {
            while (gameRunning) { // 每0.1秒循環
                delay(100)
                circleX += 10

                // 當圓碰到右邊邊界時
                if (circleX >= screenWidthPx - 100){
                    circleX = 100f
                    score += 1  // 分數加1
                }

                // 讓三匹馬都跑
                var hasWinner = false
                for (i in 0..2) {
                    horses[i].HorseRun()

                    // 檢查是否有馬抵達終點
                    if (horses[i].horseX >= screenWidthPx - 300 && !hasWinner) {
                        winnerText = "第${i+1}馬獲勝"
                        hasWinner = true
                        score += 1  // 獲勝加分

                        // 所有馬回到起點
                        delay(1000)  // 顯示獲勝訊息1秒
                        for (j in 0..2) {
                            horses[j].horseX = 0
                        }
                        winnerText = ""  // 清空獲勝訊息,準備下一輪
                    }
                }
            }
        }
    }

    fun MoveCircle(x: Float, y: Float) {
        circleX += x
        circleY += y
    }
}