package emu.gameboy

/**
* Object useful for formatting Strings.
*/
object FormatUtil
{
    /**
    * Pads a byte hex string with a zero at start
    * if its length is 1.  Otherwise, returns original
    */
    def padHexByte(str:String):String =
        if(str.length == 1) "0" + str
        else str

    /**
    * Pads a byte hex string with a zero at start
    * if its length is 1.  Otherwise, returns original
    */
    def padHexShort(str:String):String = str.length match
    {
        case 0 => "0000"
        case 1 => "000" + str
        case 2 => "00" + str
        case 3 => "0"
        case _ => str
    }
}
