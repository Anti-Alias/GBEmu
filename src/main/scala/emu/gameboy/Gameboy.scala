package emu.gameboy


/**
* Represents one Gameboy device coupled with memory and a CPU.
*/
class Gameboy
{
    /**
    * CPU of the Gameboy
    */
    val cpu = new CPU(this)

    /**
    * Main memory of the Gameboy
    */
    val memory:Memory = Memory.allocate(1024 * 64)
    val memRomBank:Memory = memory.sub(0, 0x4000)
    val memSwitchableRomBank:Memory = memory.sub(0x4000, 0x8000)
    val memVideoRam:Memory = memory.sub(0x8000, 0xA000)
    val memSwitchableRamBank:Memory = memory.sub(0xA000, 0xC000)
    val memInternalRam:Memory = memory.sub(0xC000, 0xE000)
    val memEchoInternalRam:Memory = memory.sub(0xE000, 0xFE00)
    val memSpriteAtribMemory:Memory = memory.sub(0xFE00, 0xFEA0)
    val memEmptyIO:Memory = memory.sub(0xFEA0, 0xFF00)
    val memIOPorts:Memory = memory.sub(0xFF00, 0xFF4C)
    val memEmptyIO2:Memory = memory.sub(0xFF4C, 0xFF80)
    val memInternalRam2:Memory = memory.sub(0xFF80, 0xFFFF)
    val memInterruptEnableRegister:Memory = memory.sub(0xFFFF, 0xFFFF+1)


    /**
    * Sets memory address to specified value.
    * This method will write memory in two places
    * if within a certain boundary.
    */
    def memSet8(address:Int, value:Int):Unit =
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
    * Sets memory address to specified value 16-bit value in
    * a little-endian fashion.
    * This method will write memory in two places
    * if within a certain boundary.
    */
    def memSet16(address:Int, value:Int):Unit =
    {
        val left:Int = (value >>> 8) & 0xFF
        val right:Int = value & 0xFF
        memSet8(address, right)
        memSet8(address+1, left)
    }


    /**
    * Gets memory at address specified
    */
    def memGet8(address:Int):Int = memory(address)

    /**
    * Gets memory ad attress specified in a little-endian fashion.
    */
    def memGet16(address:Int):Int =
    {
        val left:Int = memory(address+1)
        val right:Int = memory(address)
        left + (right << 8)
    }
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
