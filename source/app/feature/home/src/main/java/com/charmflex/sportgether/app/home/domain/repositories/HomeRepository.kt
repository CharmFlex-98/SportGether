package com.charmflex.sportgether.app.home.domain.repositories

internal interface HomeRepository {
    suspend fun fetchEvents()
}

internal class HomeRepositoryImpl(

) : HomeRepository {
    override suspend fun fetchEvents() {
        TODO("Not yet implemented")
    }

}