package ro.dragosivanov.tgo.di
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ro.dragosivanov.tgo.domain.api.ForeignExchangeApi
import ro.dragosivanov.tgo.domain.datasource.ForeignExchangeRemoteDataSource
import ro.dragosivanov.tgo.domain.repository.ForeignExchangeRepository
import ro.dragosivanov.tgo.domain.usecase.ForeignExchangeUseCase

class AppContainer {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://my.transfergo.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val foreignExchangeApiService = retrofit.create(ForeignExchangeApi::class.java)
    var foreignExchangeContainer: ForeignExchangeContainer? = null
}

class ForeignExchangeContainer(foreignExchangeApi: ForeignExchangeApi) {
    private val foreignExchangeRemoteDataSource =
        ForeignExchangeRemoteDataSource(foreignExchangeApi)
    private val foreignExchangeRepository =
        ForeignExchangeRepository(foreignExchangeRemoteDataSource)
    val foreignExchangeUseCase = ForeignExchangeUseCase(foreignExchangeRepository)
}

class ForeignExchangeViewModelFactory(private val foreignExchangeUseCase: ForeignExchangeUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(ForeignExchangeUseCase::class.java)
            .newInstance(foreignExchangeUseCase)
    }
}
