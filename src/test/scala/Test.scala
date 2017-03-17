import emu.gameboy.{Gameboy, CPU, Memory}

/**
* Tests running a Gameboy
*/
object Test
{
    /**
    * Main method
    */
    def main(args:Array[String])
    {
        val gb = Gameboy.create()   // Makes Gameboy
        val cpu = gb.cpu            // Gets CPU

        println(cpu.toPrettyString)

        separate()

        cpu.setFlagSubtract()
        println(cpu.toPrettyString)

        // Finishes
        println("Done!")
    }

    /**
    * Makes it easier to separate lines
    */
    private def separate():Unit = println("------------------------------------------------------------")
}
