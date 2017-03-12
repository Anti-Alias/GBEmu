package emu.gameboy


/**
* Represents one Gameboy device coupled with memory and a CPU.
*/
class Gameboy
{
    /**
    * Main memory of the Gameboy
    */
    val memory:Memory = Memory.allocate(1024 * 64)
    val romBank:Memory = memory.sub(0, 0x4000)
    val switchableRomBank:Memory = memory.sub(0x4000, 0x8000)
    val videoRam:Memory = memory.sub(0x8000, 0xA000)
    val switchableRamBank:Memory = memory.sub(0xA000, 0xC000)
    val internalRam:Memory = memory.sub(0xC000, 0xE000)
    val echoInternalRam:Memory = memory.sub(0xE000, 0xFE00)
    val spriteAtribMemory:Memory = memory.sub(0xFE00, 0xFEA0)
    val emptyIO1:Memory = memory.sub(0xFEA0, 0xFF00)
    val IOPorts:Memory = memory.sub(0xFF00, 0xFF4C)
    val emptyIO2:Memory = memory.sub(0xFF4C, 0xFF80)
    val internalRam2:Memory = memory.sub(0xFF80, 0xFFFF)
    val interruptEnableRegister:Memory = memory.sub(0xFFFF, 0xFFFF+1)


    /**
    * Sets memory address to specified value.
    * This method will write memory in two places
    * if within a certain boundary.
    */
    def memSet(address:Int, value:Int):Unit =
    {
        // Sets memory
        memory(address) = value

        // Echoes memory if between specified address values
        if(address >= 0xC000 && address <= 0xDE00)
            memory(address+0x2000) = value
        else if(address >= 0xE000 && address <= 0xFE00)
            memory(address-0x2000) = value
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
