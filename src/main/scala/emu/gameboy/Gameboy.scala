package emu.gameboy


/**
* Represents one Gameboy device coupled with memory and a CPU.
*/
class Gameboy
{
    /**
    * Main memory of the Gameboy
    */
    val memory = Memory.allocate(64000)

    /**
    * CPU of the Gameboy
    */
    val cpu = new CPU(this)
}

/**
* Companion object to the Gameboy class
*/
object Gameboy
{
    /**
    * Constructs a Gameboy instance
    */
    def create():Gameboy = new Gameboy()
}
