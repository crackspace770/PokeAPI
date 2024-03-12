package com.fajar.core.data


import com.fajar.core.data.local.LocalDataSource
import com.fajar.core.data.remote.RemoteDataSource
import com.fajar.core.data.remote.network.ApiResponse
import com.fajar.core.data.remote.response.ListPokemonResponse
import com.fajar.core.data.remote.response.PokemonDetailResponse
import com.fajar.core.domain.model.Pokemon
import com.fajar.core.domain.repository.IPokemonRepository
import com.fajar.core.utils.AppExecutors
import com.fajar.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IPokemonRepository {

   // companion object {
    //    @Volatile
   //     private var instance: PokemonRepository? = null

  //      fun getInstance(
 //           remoteData: RemoteDataSource,
 //           localData: LocalDataSource,
 //           appExecutors: AppExecutors
 //       ): PokemonRepository =
 //           instance ?: synchronized(this) {
  //              instance ?: PokemonRepository(remoteData, localData, appExecutors)
  //          }
 //   }

    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> {
        return object :
           NetworkBoundResource<List<Pokemon>, ListPokemonResponse>(){

            override fun loadFromDB(): Flow<List<Pokemon>> {
                return localDataSource.getAllPokemon().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean {
                return data == null || data.isEmpty() || data.size <= 15
            }

            override suspend fun createCall(): Flow<ApiResponse<ListPokemonResponse>> =
                remoteDataSource.getAllPokemon()

            override suspend fun saveCallResult(data: ListPokemonResponse) {
                val pokemon = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemon(pokemon)
            }
        }.asFlow()
    }




    override fun getDetailPokemon(pokemon: Pokemon): Flow<Resource<Pokemon>> {
        return object : com.fajar.core.data.NetworkOnlyResource<Pokemon, PokemonDetailResponse>() {
            override suspend fun createCall(): Flow<ApiResponse<PokemonDetailResponse>> {
                return remoteDataSource.getPokemonDetail(pokemon.name.toString())
            }

            override suspend fun loadFromNetwork(data: PokemonDetailResponse): Flow<Pokemon> {
                return flowOf(DataMapper.mapDetailPokemonResponseToDomain(data))
            }
        }.asFlow()
    }

    //fragment search
    override fun getSearchPokemon(name: String): Flow<Resource<List<Pokemon>>> {
        return object :
            NetworkOnlyResource<List<Pokemon>, ListPokemonResponse>() {

            override suspend fun createCall(): Flow<ApiResponse<ListPokemonResponse>> {
                return remoteDataSource.searchPokemon(name)
            }

            override suspend fun loadFromNetwork(data: ListPokemonResponse): Flow<List<Pokemon>> {
                return flowOf(DataMapper.mapSearchResponseDomain(data))
            }
        }.asFlow()
    }

    override fun getFavoritePokemon(): Flow<List<Pokemon>> {
        return localDataSource.getFavoritePokemon().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, state: Boolean) {
        val tourismEntity = DataMapper.mapDomainToEntity(pokemon)
        appExecutors.diskIO().execute {
            runBlocking {
                localDataSource.setFavoritePokemon(tourismEntity, state)
            }
        }
    }
}


