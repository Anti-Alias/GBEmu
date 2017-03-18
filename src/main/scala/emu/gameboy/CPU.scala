package emu.gameboy
import scala.annotation.{switch}
import FormatUtil.{padHexByte, padHexShort}
import play.api.libs.json._

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
    * Register pair getter
    */
    def BC:Int = (B << 8) + C
    def DE:Int = (D << 8) + E
    def HL:Int = (H << 8) + L


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
    * Register pair setter
    */
    def BC_=(n:Int):Unit =
    {
        val left:Int = (n >>> 8) & 0xFF
        val right:Int = n & 0xFF
        B = left
        C = right
    }
    def DE_=(n:Int):Unit =
    {
        val left:Int = (n >>> 8) & 0xFF
        val right:Int = n & 0xFF
        D = left
        E = right
    }
    def HL_=(n:Int):Unit =
    {
        val left:Int = (n >>> 8) & 0xFF
        val right:Int = n & 0xFF
        H = left
        L = right
    }

    /**
    * Flag helper functions
    */
    def flagCarry:Boolean =     (F & 16) != 0
    def flagHalfCarry:Boolean = (F & 32) != 0
    def flagSubtract:Boolean =  (F & 64) != 0
    def flagZero:Boolean =      (F & 128) != 0

    /**
    * FLag setters
    */
    def setFlagCarry():Unit =     F |= 16
    def setFlagHalfCarry():Unit = F |= 32
    def setFlagSubtract():Unit =  F |= 64
    def setFlagZero():Unit =      F |= 128

    /**
    * Flag unsetters
    */
    def unsetFlagCarry():Unit =     F &= 239
    def unsetFlagHalfCarry():Unit = F &= 223
    def unsetFlagSubtract():Unit =  F &= 191
    def unsetFlagZero():Unit =      F &= 127

    /**
    * Sets carry flag to specified value
    */
    def setFlagCarry(value:Boolean):Unit =
        if(value) setFlagCarry
        else unsetFlagCarry

    def setFlagHalfCarry(value:Boolean):Unit =
        if(value) setFlagHalfCarry
        else unsetFlagHalfCarry

    def setFlagSubtract(value:Boolean):Unit =
        if(value) setFlagSubtract
        else unsetFlagSubtract

    def setFlagZero(value:Boolean):Unit =
        if(value) setFlagZero
        else unsetFlagZero

    /**
    * Determines if computation from state A to state B resulted in
    * a half-carry.
    */
    def isHalfCarry(a:Int, b:Int):Boolean =
    {
        val bit3A = (a >>> 3) & 0x1
        val bit3B = (a >>> 3) & 0x1
        bit3A != bit3B
    }

    /**
    * Determines if computation from state A to state B resulted in
    * a carry.
    */
    def isCarry(a:Int, b:Int):Boolean =
    {
        val bit7A = (a >>> 7) & 0x1
        val bit7B = (b >>> 7) & 0x1
        bit7A != bit7B
    }

    /**
    * JSON representation of the CPU
    */
    def toJSON:JsObject = Json.obj(
        "registers" -> Json.obj(
            "A" -> padHexByte(A.toHexString),
            "F" -> padHexByte(F.toHexString),
            "B" -> padHexByte(B.toHexString),
            "C" -> padHexByte(C.toHexString),
            "D" -> padHexByte(D.toHexString),
            "E" -> padHexByte(E.toHexString),
            "H" -> padHexByte(H.toHexString),
            "L" -> padHexByte(L.toHexString),
            "SP" -> padHexShort(SP.toHexString),
            "PC" -> padHexShort(PC.toHexString)
        ),
        "flags" -> Json.obj(
            "Z" -> flagZero,
            "N" -> flagSubtract,
            "H" -> flagHalfCarry,
            "C" -> flagCarry
        )
    )

    /**
    * String representation of the current state of the CPU as JSON.
    */
    override def toString:String = toJSON.toString

    /**
    * Represents CPU as a pretty-printed JSON String.
    */
    def toPrettyString:String = Json.prettyPrint(toJSON)

    /**
    * 0x00
    */
    def op_NOP():Unit = {}

    /**
    * 0x01
    */
    def op_LD_BC_d16(num:Int):Unit = BC = num

    /**
    * 0x02
    */
    def op_LD_mem_BC_A():Unit = gameboy.memSet8(BC, A)

    /**
    * 0x03
    */
    def op_INC_BC():Unit = BC += 1

    /**
    * 0x04
    */
    def op_INC_B():Unit =
    {
        val old:Int = B
        B += 1
        setFlagZero(B == 0)
        unsetFlagSubtract
        setFlagHalfCarry(isCarry(old, B))
    }

    /**
    * 0x05
    */
    def op_DEC_B():Unit =
    {
        val old:Int = B
        B -= 1
        setFlagZero(B == 0)
        setFlagSubtract
        setFlagHalfCarry(isHalfCarry(old, B))
    }

    /**
    * 0x06
    */
    def op_LD_B_d8(num:Int):Unit = B == num

    /**
    * 0x07
    */
    def op_RLCA():Unit = throw new RuntimeException("RLCA not implemented")

    /**
    * 0x08
    */
    def op_LD_mem_a16_SP(num:Int):Unit = gameboy.memSet16(num, SP)

    /**
    * 0x09
    */
    def op_ADD_HL_BC():Unit =
    {
        val old:Int = HL
        HL += BC
        unsetFlagSubtract
        setFlagHalfCarry(isHalfCarry(old, HL))
        setFlagCarry(isCarry(old, HL))
    }

    /**
    * 0x0A
    */
    def op_LD_A_mem_BC():Unit = A = gameboy.memGet8(BC)

    /**
    * 0x0B
    */
    def op_DEC_BC():Unit = BC -= 1

    /**
    * 0x0C
    */
    def op_INC_C():Unit =
    {
        val old:Int = C
        C += 1
        if(C == 0) setFlagZero
        unsetFlagSubtract
        setFlagHalfCarry(isHalfCarry(old, C))
    }

    /**
    * 0x0D
    */
    def op_DEC_C():Unit =
    {
        val old:Int = C
        C -= 1
        if(C == 0) setFlagZero
        setFlagSubtract
        setFlagHalfCarry(isHalfCarry(old, C))
    }

    /**
    * 0x0E
    */
    def op_LD_C_d8(num:Int):Unit = C = num

    /**
    * 0x0F
    */
    def op_RRCA():Unit = notImp()
    
    /**
     * 0x10
     */
    def op_STOP():Unit = notImp()
    
    /**
     * 0x11
     */
    def op_LD_DE_d16(num:Int):Unit = DE = num
    
    /**
     * 0x12
     */
    def op_LD_mem_DE_A():Unit = gameboy.memSet8(DE, A)
    
    /**
     * 0x13
     */
    def op_INC_DE():Unit = DE += 1
    
    /**
     * 0x14
     */
    def op_INC_D():Unit = D += 1

    /**
     * 0x15
     */
    def op_DEC_D():Unit =
    {
        val old:Int = D
        D -= 1
        setFlagZero(D == 0)
        setFlagSubtract
        setFlagHalfCarry(isHalfCarry(old, D))
    }
    
    /**
     * Convenience method used to denote a method that is not implemented
     */
    def notImp():Unit = throw new RuntimeException("Not implemented")

    /**
    * Reads next 8 bits from PC.
    * Advances PC past those bits.
    */
    def read8():Int =
    {
        val value:Int = gameboy.memGet8(PC)
        PC += 1
        value
    }

    /**
    * Reads next 16 bits from PC.
    * Advances PC past those bits
    */
    def read16():Int =
    {
        val right:Int = gameboy.memGet8(PC)
        val left:Int = gameboy.memGet8(PC+1)
        PC += 2
        (left << 8) + right
    }

    /**
    * Runs next instruction
    */
    def execute():Unit =
    {
        // Reads next opcode
        val opcode:Int = read8()

        // Interprests opcode
        (opcode: @switch) match
        {
            case 0x00 => op_NOP()
            case 0x01 => op_LD_BC_d16(read16())
            case 0x02 => op_LD_mem_BC_A()
            case 0x03 => op_INC_BC()
            case 0x04 => op_INC_B()
            case 0x05 => op_DEC_B()
            case 0x06 => op_LD_B_d8(read8())
            case 0x07 => op_RLCA()
            case 0x08 => op_LD_mem_a16_SP(read16())
            case 0x09 => op_ADD_HL_BC()
            case 0x0A => op_LD_A_mem_BC()
            case 0x0B => op_DEC_BC()
            case 0x0C => op_INC_C()
            case 0x0D => op_DEC_C()
            case 0x0E => op_LD_C_d8(read8())
            case 0x0F => op_RRCA()
            case _ => throw new RuntimeException("Invalid opcode " + opcode.toHexString)
        }
    }
}
