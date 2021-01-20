package net.be_geek.petclinic.entity

data class PetsView(
    val pets: Pets,
    val types: Types,
    val visits: Visits?
)
