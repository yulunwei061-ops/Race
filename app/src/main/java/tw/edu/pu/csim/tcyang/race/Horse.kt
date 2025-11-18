package tw.edu.pu.csim.tcyang.race

import kotlin.random.Random

class Horse() {
    var horseX = 0
    var horseY = 100

    var number = 0

    fun HorseRun(){
        number++
        if (number > 3) { number = 0 }
        horseX += Random.nextInt(10, 31)
    }
}