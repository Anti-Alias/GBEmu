package emu.gameboy

/**
* Represents the CPU of Gameboy
* @param gameboy Gameboy that this CPU belongs to.
*/
class CPU private[gameboy](val gameboy:Gameboy)
{
    /**
    * 8-bit register
    */
    private var _A, _F, _B, _C, _D, _E, _H, _L = 0

    /**
    * 16-bit register
    */
    private var _SP, _PC = 0

    /**
    * Register getter
    */
    def A:Int = _A & 0xFF
    def F:Int = _F & 0xFF
    def B:Int = _B & 0xFF
    def C:Int = _C & 0xFF
    def D:Int = _D & 0xFF
    def E:Int = _E & 0xFF
    def H:Int = _H & 0xFF
    def L:Int = _L & 0xFF
    def SP:Int = _SP & 0xFFFF
    def PC:Int = _PC & 0xFFFF

    /**
    * Register setter
    */
    def A_=(n:Int):Unit = _A = n
    def F_=(n:Int):Unit = _F = n
    def B_=(n:Int):Unit = _B = n
    def C_=(n:Int):Unit = _C = n
    def D_=(n:Int):Unit = _D = n
    def E_=(n:Int):Unit = _E = n
    def H_=(n:Int):Unit = _H = n
    def L_=(n:Int):Unit = _L = n
    def SP_=(n:Int):Unit = _SP = n
    def PC_=(n:Int):Unit = _PC = n

    /**
    * Flag helper functions
    */
    def carryFlag:Boolean =     (F & 16) != 0
    def halfCarryFlag:Boolean = (F & 32) != 0
    def subtractFlag:Boolean =  (F & 64) != 0
    def zeroFlag:Boolean =      (F & 128) != 0

    /**
    * FLag setters
    */
    def setCarry:Unit =        F |= 16
    def setHalfCarry:Unit =    F |= 32
    def setSubtractFlag:Unit = F |= 64
    def setZeroFlag:Unit =     F |= 128


    /**
    * Memory accessor
    */
    def memory:Memory = gameboy.memory
}
