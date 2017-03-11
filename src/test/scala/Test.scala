import emu.gameboy.{Gameboy, CPU, Memory}

/**
* Tests running a Gameboy
*/
object Test extends App
{
    val gb = Gameboy.create()   // Makes Gameboy

    println("Done!")            // Finishes emulation
}
