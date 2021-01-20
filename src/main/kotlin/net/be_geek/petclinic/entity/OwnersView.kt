package net.be_geek.petclinic.entity

data class OwnersView(
    val owners: Owners,
    val pets: Pets?,
    val types: Types?
)
