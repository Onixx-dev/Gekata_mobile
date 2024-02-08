package com.example.gekata_mobile.Network.TransportModels

import com.fasterxml.jackson.annotation.JsonProperty

data class TransportBuilding(
    @JsonProperty("id"   ) var id   : Int?            = null,
    @JsonProperty("name" ) var name : String?         = null,
    @JsonProperty("adress"  ) var address  : String?         = null,
    @JsonProperty("path") var path : String? = null,
    @JsonProperty("google_adress"  ) var googleAddress   : String?         = null,
    @JsonProperty("yandex_adress"  ) var yandexAddress   : String?         = null,
    @JsonProperty("osm_adress"  ) var osmAddress   : String?         = null,
    @JsonProperty("twoGis_adress"  ) var twoGisAddress   : String?         = null
)
