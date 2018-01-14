package gunman

import (
    "fmt"
    "time"
    "math"
)

type GunClock struct {
    clockSize int
    gunClockString string
    gunClockSlices [][]byte

    centerX int
    centerY int
    gunmanX int
    gunmanY int
    inuX int
    inuY int
}

func NewGunClock(clockSize int) *GunClock {
//    fmt.Printf("kita--.")

    gunClock := &GunClock{
                   clockSize: clockSize, 
                   gunClockString: "abcde",
//                   gunClockSlices: make([][]byte, clockSize, clockSize)
                }

    gunClock.gunClockSlices = make([][]byte, clockSize, clockSize)
    for i:=0; i<clockSize; i++ {
        gunClock.gunClockSlices[i] = make([]byte, clockSize*2, clockSize*2)
        for j:=0; j<clockSize*2; j++ {
            gunClock.gunClockSlices[i][j] = ' '
        }
    }

    gunClock.createGunClock()


    return gunClock
}

func (this *GunClock) createGunClock() {

    this.centerX = this.clockSize / 2
    this.centerY = this.clockSize / 2

//_ = centerX 
//_ = centerY
    var hour, minute, second int = getTime()

    this.gunmanX = this.centerX + int(math.Cos(hourToRadian(hour, minute)) * (float64(this.clockSize) * 2/3/2) )
    this.gunmanY = this.centerY - int(math.Sin(hourToRadian(hour, minute)) * (float64(this.clockSize) * 2/3/2) )
    this.inuX = this.centerX + int(math.Cos(minuteToRadian(minute, second)) * (float64(this.clockSize) * 4/5/2) )
    this.inuY = this.centerY - int(math.Sin(minuteToRadian(minute, second)) * (float64(this.clockSize) * 4/5/2) )

//fmt.Printf("[" + string(hour) + ":" + string(minute) + ":" + string(second) + "]")
//fmt.Printf("[%02d:%02d:%02d]", hour, minute, second)

    gunman := NewCast([]string{ "** __ *", " _|__|_", "b (@@) ", " V|~~|>", "* //T| " }, this)

    inu := NewCast([]string{ "__AA  **", "| 6 |__P", "~~|    l", "*/_/~l_l" }, this)

//    inu := NewCast([]string{"ab", "cd"}, this)


    waku := NewCast([]string{"+"}, this)
    wakuDisplay( waku )

    num3 := NewCast([]string{"3"}, this)
    num6 := NewCast([]string{"6"}, this)
    num9 := NewCast([]string{"9"}, this)
    num12 := NewCast([]string{"12"}, this)
    numDisplay( num3, num6, num9, num12 )

    longHand := NewCast([]string{"##"}, this)
    longHandDisplay( longHand )
    shortHand := NewCast([]string{"::"}, this)
    shortHandDisplay( shortHand )

    inu.display(this.inuX,this.inuY)
    gunman.display(this.gunmanX,this.gunmanY)

    var timeString = fmt.Sprintf("%02d:%02d", hour, minute)
    digitalTime := NewCast([]string{"_______", "|"+timeString+"|", "~~~~~~~"} , this )
    digitalTimeDisplay(hour, minute, second, digitalTime)
}

func (this *GunClock) GetGunClockString() string {
    return this.gunClockString
}

func (this *GunClock) GetGunClock() string {
    var retString string
    retString = ""
    for i:=0; i<len(this.gunClockSlices); i++ {
        retString = retString + string(this.gunClockSlices[i]) + "\n"
//        for j:=0; j<len(this.gunClockSlices[i]); j++ {
//            retString := retString + this.gunClockSlices[i][j]
//        }
    }
    return retString
}

func (this *GunClock) SetGunClockByte(x int, y int, b byte) {
    if x<0 { return }
    if y<0 { return }
    if x>=(len(this.gunClockSlices[0])) { return }
    if y>=len(this.gunClockSlices) { return }
    if b == '*' { return}

    this.gunClockSlices[y][x] = b
}


type Cast struct {
   image [][]byte
   gunClock *GunClock
}

func _NewCast(image []string) *Cast{
    newCast := &Cast{}
    return newCast
}

func NewCast(image []string, gunClock *GunClock) *Cast {
    newCast := &Cast{gunClock: gunClock}
    newCast.image = make([][]byte, len(image), len(image))
    for i:=0; i<len(image); i++ {
        newCast.image[i] = []byte(image[i])
    }
    return newCast
}

func (cast *Cast) display (x int, y int) {
    var _x = (x * 2) - len(cast.image[0]) / 2
    var _y = (y    ) - len(cast.image   ) / 2

    for i:=0; i<len(cast.image); i++ {
        for j:=0; j<len(cast.image[0]); j++ {
            cast.gunClock.SetGunClockByte(_x+j, _y+i, cast.image[i][j])
        }
    }
   
}

const location = "Asia/Tokyo"
func getTime() (hour, minute, second int) {
    loc, err := time.LoadLocation(location)
    if err != nil {
        loc = time.FixedZone(location, 9*60*60)
    }

    t := time.Now().In(loc)
    return t.Clock()  //hour,min,second
//    return t.Hour(), t.Minute(), t.Second()
}

func hourToRadian(h, m int) (float64) {
    var r float64 = math.Pi * (90.0 - (float64(h%12) + float64(m/60.0)) * 30.0) / 180.0;
    return r
}

func minuteToRadian(m, s int) (float64) {
    var r float64 = math.Pi * (90.0 - (float64(m) + float64(s/60.0)) * 6.0) / 180.0;
    return r
}

func wakuDisplay(waku *Cast) {
    for i:=0; i<360; i+=30 {
        if  i % 90 != 0 {

            var radian float64 = ( float64(i * 2) * math.Pi ) / 360
            var wakuXdiff float64 = float64(waku.gunClock.clockSize)/2 * math.Cos(radian)
            var wakuYdiff float64 = float64(waku.gunClock.clockSize)/2 * math.Sin(radian)

            var wakuX int = 0
            var wakuY int = 0
            if ( wakuXdiff >= 0 ) {
                wakuX = waku.gunClock.centerX + int(math.Floor(wakuXdiff))
            } else {
                wakuX = waku.gunClock.centerX + int(math.Ceil(wakuXdiff))
            }

            if ( wakuYdiff >= 0 ) {
                wakuY = waku.gunClock.centerY + int(math.Floor(wakuYdiff))
            } else {
                wakuY = waku.gunClock.centerY + int(math.Ceil(wakuYdiff))
            }

            waku.display(wakuX, wakuY)
        }
    }
}

func numDisplay(num3, num6, num9, num12 *Cast) {
    num3.display(num3.gunClock.clockSize - 1, num3.gunClock.centerY)
    num6.display(num6.gunClock.centerX, num6.gunClock.clockSize - 1)
    num9.display(0, num9.gunClock.centerY)
    num12.display(num12.gunClock.centerX, 0)
}

func longHandDisplay(longHand *Cast) {
//    gunClock GunClock = longHand.gunClock
    for i:=0; i<int(float64(longHand.gunClock.clockSize)*2/3/2); i++ {
        var longHandX int = longHand.gunClock.centerX + int( float64( (longHand.gunClock.inuX - longHand.gunClock.centerX) * i ) / (float64(longHand.gunClock.clockSize)*2/3/2) )
        var longHandY int = longHand.gunClock.centerY + int( float64( (longHand.gunClock.inuY - longHand.gunClock.centerY) * i ) / (float64(longHand.gunClock.clockSize)*2/3/2) )

        longHand.display(longHandX, longHandY)
    }
}

func shortHandDisplay(shortHand *Cast) {
//    gunClock GunClock = longHand.gunClock
    for i:=0; i<int(float64(shortHand.gunClock.clockSize)*5/6/2); i++ {
        var shortHandX int = shortHand.gunClock.centerX + int( float64( (shortHand.gunClock.gunmanX - shortHand.gunClock.centerX) * i ) / (float64(shortHand.gunClock.clockSize)*5/6/2) )
        var shortHandY int = shortHand.gunClock.centerY + int( float64( (shortHand.gunClock.gunmanY - shortHand.gunClock.centerY) * i ) / (float64(shortHand.gunClock.clockSize)*5/6/2) )
        shortHand.display(shortHandX, shortHandY)
    }
}

func digitalRadian(h, m, s int) (float64) {
    var hRadian float64 = hourToRadian(h, m)
    var mRadian float64 = minuteToRadian(m, s)
    var aveRadian float64 = (hRadian + mRadian) / 2

    if ( hRadian > mRadian && (hRadian - mRadian) < math.Pi ) || ( mRadian > hRadian && (mRadian - hRadian) < math.Pi ) {
        return (aveRadian + math.Pi)
    } else { 
        return aveRadian
    }
}

func digitalTimeDisplay(hour, minute, second int, digitalTime *Cast) {

    var digitalTimeRadian float64 = digitalRadian(hour,minute,second)
    digitalTime.display(
        digitalTime.gunClock.centerX + int(math.Cos(digitalTimeRadian) * float64(digitalTime.gunClock.clockSize)/2 * 1/2),
        digitalTime.gunClock.centerY - int(math.Sin(digitalTimeRadian) * float64(digitalTime.gunClock.clockSize)/2 * 1/2) )
}

