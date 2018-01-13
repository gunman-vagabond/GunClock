package gunman

import java.util.{Calendar,TimeZone,Locale}
import scala.math
import scala.collection.immutable.List

class GunClock(clockSize: Int) {

    // private values ////////////////////////////////////////////////////////
    var gunClock:String = ""
    var gunClocks:List[String] = List()
    var gunClockChars:List[List[Char]] = List(List())
    var gunClockArray:Array[Array[Char]] = Array.ofDim[Char](clockSize,clockSize*2)
//    private String         strNewline = "<br>\n";
    var strNewline:String = "\n";

    var this.clockSize:Int = clockSize;
    var centerX:Int = this.clockSize / 2
    var centerY:Int = this.clockSize / 2

    var (hour, minute, second) = getTime()

    var gunmanX:Int = centerX + (Math.cos(hourToRadian(hour, minute)) * (clockSize * 2/3/2) ).asInstanceOf[Int]
    var gunmanY:Int = centerY - (Math.sin(hourToRadian(hour, minute)) * (clockSize * 2/3/2) ).asInstanceOf[Int]
    var inuX:Int = centerX + (Math.cos(minuteToRadian(minute, second)) * (clockSize * 4/5/2) ).asInstanceOf[Int]
    var inuY:Int = centerY - (Math.sin(minuteToRadian(minute, second)) * (clockSize * 4/5/2) ).asInstanceOf[Int]

    for ( i <- 0 to gunClockArray.length - 1 ) {
        for ( j <- 0 to gunClockArray(0).length - 1 ) {
            (gunClockArray(i))(j) = ' '
        }
    }

    createGunClock()

    def createGunClock() = {
        var gunman = new Cast( Array(
            "** __ *", 
            " _|__|_",
            "b (@@) ",
            " V|~~|>",
            "* //T| "
        ) )
        var inu = new Cast ( Array(
            "__AA  **",
            "| 6 |__P",
            "~~|    l",
            "*/_/~l_l"
        ) )

        wakuDisplay( new Cast(Array("+")) )
        numDisplay( new Cast(Array("3")), new Cast(Array("6")), new Cast(Array("9")), new Cast(Array("12")) )
        longHandDisplay( new Cast(Array("##")) )
        shortHandDisplay( new Cast(Array("::")) )
        inu.display(inuX, inuY)

        gunman.display(gunmanX, gunmanY)

        digitalTimeDisplay(hour, minute, second)

    }



    class Cast(image: Array[String]) {
        var this.image = image

        def display (x: Int, y: Int) = {
            var _x: Int = ( x * 2 ) - this.image(0).length / 2
            var _y: Int = y         - this.image.length    / 2

            for ( i <- 0 to this.image.length - 1 ) {
                for ( j <- 0 to this.image(i).length - 1 ) {
                    if ( image(i)(j) != '*' ) {
                        gunClockArray(_y+i)(_x+j) = image(i)(j) 
                    }
                }
            }
        }
    }

    def getTime() = {
        var calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"), Locale.JAPAN);
        var hour:Int   = calendar.get(Calendar.HOUR_OF_DAY);
        var minute:Int = calendar.get(Calendar.MINUTE);
        var second:Int = calendar.get(Calendar.SECOND);

        (hour, minute, second)

    }

    def hourToRadian(h:Int, m:Int) = {
        Math.PI * (90.0 - ((h%12) + m/60.0) * 30.0) / 180.0;
    }

    def minuteToRadian(m:Int, s:Int) = {
        Math.PI * (90.0 - (m + s/60.0) * 6.0) / 180.0;
    }

    def wakuDisplay(waku:Cast) = {
        for ( i <- 0 to 360 by 30 ) {
            if ( i % 90 != 0 ) {

                var radian = ( i * 2 * Math.PI ) / 360
                var wakuXdiff = this.clockSize/2 * Math.cos(radian)
                var wakuYdiff = this.clockSize/2 * Math.sin(radian)

                var wakuX, wakuY = 0
                if ( wakuXdiff >= 0 ) {
                    wakuX = centerX + Math.floor(wakuXdiff).asInstanceOf[Int]
                } else {
                    wakuX = centerX + Math.ceil(wakuXdiff).asInstanceOf[Int]
                }

                if ( wakuYdiff >= 0 ) {
                    wakuY = centerY + Math.floor(wakuYdiff).asInstanceOf[Int]
                } else {
                    wakuY = centerY + Math.ceil(wakuYdiff).asInstanceOf[Int]
                }

//#    print "%s %s" % (wakuX , wakuY)
//println("wakuX=" + wakuX + ",wakuY=" + wakuY + "\n")

                waku.display(wakuX, wakuY)
            }
        }
    }

    def numDisplay(num3:Cast, num6:Cast, num9:Cast, num12:Cast) {
        num3.display(clockSize - 1, centerY)
        num6.display(centerX, clockSize - 1)
        num9.display(0, centerY)
        num12.display(centerX, 0)
    }

    def longHandDisplay(longHand:Cast) = {
        for ( i <- 0 to (clockSize*2/3/2).asInstanceOf[Int] ) {
            var longHandX = centerX + ( ( (inuX - centerX) * i ) / (clockSize*2/3/2) ).asInstanceOf[Int]
            var longHandY = centerY + ( ( (inuY - centerY) * i ) / (clockSize*2/3/2) ).asInstanceOf[Int]
            longHand.display(longHandX, longHandY)
        }
    }

    def shortHandDisplay(shortHand:Cast) = {
        for ( i <- 0 to (clockSize*5/6/2).asInstanceOf[Int] ) {
            var shortHandX = centerX + ( ( (gunmanX - centerX) * i ) / (clockSize*5/6/2) ).asInstanceOf[Int]
            var shortHandY = centerY + ( ( (gunmanY - centerY) * i ) / (clockSize*5/6/2) ).asInstanceOf[Int]
            shortHand.display(shortHandX, shortHandY)
        }
    }

    def digitalTimeDisplay(hour:Int, minute:Int, second:Int) = {
        var digitalTime = new Cast(Array(
//    "__________",
//    f"|$hour%02d:$minute%02d:$second%02d|" ,
//    "~~~~~~~~~~"
            "_______",
            f"|$hour%02d:$minute%02d|" ,
            "~~~~~~~"
        ))

        var digitalTimeRadian = digitalRadian(hour,minute,second)
        digitalTime.display(
            centerX + (Math.cos(digitalTimeRadian) * clockSize/2 * 1/2).asInstanceOf[Int],
            centerY - (Math.sin(digitalTimeRadian) * clockSize/2 * 1/2).asInstanceOf[Int]
        )
    }

    def digitalRadian(h:Int, m:Int, s:Int) = {
        var hRadian = hourToRadian(h, m)
        var mRadian = minuteToRadian(m, s)
        var aveRadian = (hRadian + mRadian) / 2

        if ( ( (hRadian > mRadian) && (hRadian - mRadian < Math.PI) )
          || ( (mRadian > hRadian) && (mRadian - hRadian < Math.PI) ) ) {
            aveRadian + Math.PI
        } else { 
            aveRadian
        }
    }

    override def toString = {
        var retString = ""

        for ( i <- 0 to gunClockArray.length - 1 ) {
            for ( j <- 0 to gunClockArray(0).length - 1 ) {
                retString += (gunClockArray(i))(j)
            }
            retString += strNewline
        }

        retString
    }

}

object GunClockTest {
    def main(args:Array[String]) {
        var gunclock : GunClock = new GunClock(clockSize = 15)
        println(gunclock.toString())
        println(gunclock)

        println("Hello")
        println("sin(30)=" + math.sin( math.toRadians(30)))
        val now = Calendar.getInstance()
        println(now.get(Calendar.HOUR) + ":" + now.get(Calendar.MINUTE))
    }
}