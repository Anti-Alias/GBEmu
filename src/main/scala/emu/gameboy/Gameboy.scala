package emu.gameboy


/**
* Represents one Gameboy device coupled with memory and a CPU.
*/
class Gameboy
{
    /**
    * Main memory of the Gameboy
    */
    val memory:Memory = Memory.allocate(64000)
    val romBank:Memory = memory.sub(0, 0x4000)
    val switchableRomBank:Memory = memory.sub(0x4000, 0x4000)
    val videoRam:Memory = memory.sub(0x8000, 0x2000)
    val switchableRamBank:Memory = memory.sub(0xA000, 0x2000)
    val internalRam:Memory = memory.sub(0xC000, 0x2000)


    /**
    * Sets memory address to specified value.
    * This method will write memory in two places
    * if within a certain boundary.
    */
    def memSet(address:Int, value:Int):Unit =
    {
        memory(address) = value // Sets memory
    }


    /**
    * Gets memory at address specified
    */
    def memGet(address:Int):Int = memory(address)

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
