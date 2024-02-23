package com.fajar.pokeapi.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.fajar.pokeapi.core.domain.model.Pokemon
import com.fajar.pokeapi.core.domain.repository.IPokemonRepository
import com.fajar.pokeapi.core.data.local.LocalDataSource
import com.fajar.pokeapi.core.data.remote.RemoteDataSource
import com.fajar.pokeapi.core.data.remote.network.ApiResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonDetailResponse
import com.fajar.pokeapi.core.data.remote.response.PokemonResponse
import com.fajar.pokeapi.core.utils.AppExecutors
import com.fajar.pokeapi.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PokemonRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IPokemonRepository {

    companion object {
        @Volatile
        private var instance: PokemonRepository? = null

        fun getInstance(
            remoteData: RemoteDataSource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): PokemonRepository =
            instance ?: synchronized(this) {
                instance ?: PokemonRepository(remoteData, localData, appExecutors)
            }
    }

    override fun getAllPokemon(): LiveData<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<Pokemon>> {
                return localDataSource.getAllPokemon().map { DataMapper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean =
//                data == null || data.isEmpty()
                true // ganti dengan true jika ingin selalu mengambil data dari internet

            override fun createCall(): LiveData<ApiResponse<List<PokemonResponse>>> =
                remoteDataSource.getAllPokemon()

            override fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemon(pokemonList)
            }
        }.asLiveData()



    //activity detail(movie)
    override fun getDetailPokemon(pokemon: Pokemon): LiveData<Resource<Pokemon>> {
        return object : NetworkOnlyResource<Pokemon, PokemonDetailResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<PokemonDetailResponse>> {
                return remoteDataSource.getPokemonDetail(pokemon.name)
            }

            override fun loadFromNetwork(data: PokemonDetailResponse): LiveData<Pokemon> {
                val mappedPokemon = DataMapper.mapDetailPokemonResponseToDomain(data)
                val resultLiveData = MutableLiveData<Pokemon>()
                resultLiveData.postValue(mappedPokemon)
                return resultLiveData
            }
        }.asLiveData()
    }

    override fun getFavoritePokemon(): LiveData<List<Pokemon>> {
        return localDataSource.getFavoritePokemon().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(pokemon)
        appExecutors.diskIO().execute { localDataSource.setFavoritePokemon(tourismEntity, state) }
    }

}


