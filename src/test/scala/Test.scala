import emu.gameboy.{Gameboy, CPU, Memory}
import org.scalatest._

/**
* Tests running a Gameboy
*/
class Test extends FunSuite
{
  test("Decrement B1")
  {
    val gb = new Gameboy()
    val cpu = gb.cpu
    cpu.B = 2
    
    cpu.op_DEC_B()
    assert(!cpu.flagZero && cpu.flagSubtract && !cpu.flagCarry && !cpu.flagHalfCarry)
  }
  
  test("Decrement B2")
  {
    val gb = new Gameboy()
    val cpu = gb.cpu
    cpu.B = 1
    
    cpu.op_DEC_B()
    assert(cpu.flagZero && cpu.flagSubtract && !cpu.flagCarry && !cpu.flagHalfCarry)
  }
  
  test("Decrement C1")
  {
    val gb = new Gameboy()
    val cpu = gb.cpu
    cpu.C = 2
    
    cpu.op_DEC_C()
    assert(!cpu.flagZero && cpu.flagSubtract && !cpu.flagCarry && !cpu.flagHalfCarry)
  }
  
  test("Decrement C2")
  {
    val gb = new Gameboy()
    val cpu = gb.cpu
    cpu.C = 1
    
    cpu.op_DEC_C()
    assert(cpu.flagZero && cpu.flagSubtract && !cpu.flagCarry && !cpu.flagHalfCarry)
  }
}
