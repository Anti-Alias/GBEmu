package emu.gameboy
import scala.util.{Random}


/**
* Represents the memory of a Gameboy
* @param bytes Number of bytes to allocate
* @param start Start of Memory
* @param until Address+1 of last byte
*/
class Memory(val bytes:Array[Byte], val start:Int, val until:Int)
{
    // Checks args
    require(until <= bytes.length, "Out of bounds")

    /**
    * Acquires byte at specified index
    */
    def apply(index:Int):Int = bytes(start+index)

    /**
    * Sets Byte at given index
    */
    def update(index:Int, value:Byte):Unit = bytes(start+index) = value

    /**
    * Sets Byte at given index
    */
    def update(index:Int, value:Int):Unit = update(index, value.toByte)

    /**
    * @return slice of this Memory object.  Points to same memory allocation.  Side effects apply.
    * @param start Start of memory object relative to current.
    * @param until Address+1 of last byte
    */
    def sub(start:Int, until:Int):Memory = new Memory(bytes, start+this.start, until)

    /**
    * Constructs a hexidecimal String of every byte in Memory
    */
    def toHexString:String = bytes
        .view
        .slice(start, until)
        .map{_.toInt}
        .map{_ & 0xFF}
        .map{_.toHexString}
        .map{FormatUtil.padHexByte}
        .mkString(" ")
}


/**
* Companion object to Memory class
*/
object Memory
{
    /**
    * Allocates memory for the first time.
    * @param rand Random object used for generating Random bytes.  This simulates
    * the state of a Gameboy when first booting up.
    */
    def allocate(numBytes:Int, rand:Random=new Random()):Memory =
    {
        val allocation:Array[Byte] = Array.fill(numBytes)(0)
        rand.nextBytes(allocation)
        new Memory(allocation, 0, numBytes)
    }
}
