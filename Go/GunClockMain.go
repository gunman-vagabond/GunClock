package main

import (
    "gunman"
    "fmt"
)

func main() {
    gunClock := gunman.NewGunClock(20)
    gunClock.SetGunClockByte(3,5,'x')

//fmt.Printf("kita--main()")
//      fmt.Printf(gunClock.GetGunClockString())
    fmt.Printf(gunClock.GetGunClock())

    _ = gunClock  //avoid not used gunClock
}

