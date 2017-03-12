import emu.gameboy.{Gameboy, CPU, Memory}

/**
* Tests running a Gameboy
*/
object Test extends App
{
    val gb = Gameboy.create()   // Makes Gameboy
    val cpu = gb.cpu
    cpu.A = 0xFFF0
    for(i <- 0 until 32)
    {
        println(cpu)
        cpu.A += 1
    }

    println("Done!")            // Finishes emulation
}
