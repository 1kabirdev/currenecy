package com.exchangerate.model

data class ResponseRate(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Rates
)

data class Rates(
    val AED: Double,
    val AFN: Double,
    val ALL: Double,
    val AMD: Double,
    val ANG: Double,
    val AOA: Double,
    val ARS: Double,
    val AUD: Double,
    val AWG: Double,
    val AZN: Double,
    val BAM: Double,
    val BBD: Double,
    val BDT: Double,
    val BGN: Double,
    val BHD: Double,
    val BIF: Double,
    val BMD: Double,
    val BND: Double,
    val BOB: Double,
    val BRL: Double,
    val BSD: Double,
    val BTC: Double,
    val BTN: Double,
    val BWP: Double,
    val BYN: Double,
    val BYR: Double,
    val BZD: Double,
    val CAD: Double,
    val CDF: Double,
    val CHF: Double,
    val CLF: Double,
    val CLP: Double,
    val CNY: Double,
    val COP: Double,
    val CRC: Double,
    val CUC: Double,
    val CUP: Double,
    val CVE: Double,
    val CZK: Double,
    val DJF: Double,
    val DKK: Double,
    val DOP: Double,
    val DZD: Double,
    val EGP: Double,
    val ERN: Double,
    val ETB: Double,
    val EUR: Double,
    val FJD: Double
) {

    fun getMapRates(): ArrayList<Pair<String, String>> {
        val map = HashMap<String, Double>()
        map["AED"] = AED
        map["AFN"] = AFN
        map["ALL"] = ALL
        map["AMD"] = AMD
        map["ANG"] = ANG
        map["AOA"] = AOA
        map["ARS"] = ARS
        map["AUD"] = AUD
        map["AWG"] = AWG
        map["AZN"] = AZN
        map["BAM"] = BAM
        map["BBD"] = BBD
        map["BDT"] = BDT
        map["BGN"] = BGN
        map["BHD"] = BHD
        map["BIF"] = BIF
        map["BMD"] = BMD
        map["BND"] = BND
        map["BOB"] = BOB
        map["BRL"] = BRL
        map["BSD"] = BSD
        map["BTC"] = BTC
        map["BTN"] = BTN
        map["BWP"] = BWP
        map["BYN"] = BYN
        map["BYR"] = BYR
        map["BZD"] = BZD
        map["CAD"] = CAD
        map["CDF"] = CDF
        map["CHF"] = CHF
        map["CLF"] = CLF
        map["CLP"] = CLP
        map["CNY"] = CNY
        map["COP"] = COP
        map["CRC"] = CRC
        map["CUC"] = CUC
        map["CUP"] = CUP
        map["CVE"] = CVE
        map["CZK"] = CZK
        map["DJF"] = DJF
        map["DKK"] = DKK
        map["DOP"] = DOP
        map["DZD"] = DZD
        map["EGP"] = EGP
        map["ERN"] = ERN
        map["ETB"] = ETB
        map["EUR"] = EUR
        map["FJD"] = FJD
        val valuesList: List<String> = map.values.map { (it.toString()) } as ArrayList<String>
        val keysList: List<String> = map.keys.map { (it) } as ArrayList<String>
        return valuesList.zip(keysList) as ArrayList<Pair<String, String>>
    }
}
