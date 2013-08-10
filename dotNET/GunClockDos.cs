using System;

public class GunClockDos {

    public static void Main(){
        GunClock gc = new GunClock();
        gc.setStrNewline("\n");
        Console.WriteLine(gc.getGunClock());
    }

}