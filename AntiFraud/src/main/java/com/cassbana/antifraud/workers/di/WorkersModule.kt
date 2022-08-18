package com.cassbana.antifraud.workers.di



import com.cassbana.antifraud.workers.simInfo.data.mapper.SIMInformationMapper
import com.bluecrunch.microfinance.workers.utils.UniqueIDGeneratorUseCase
import com.bluecrunch.microfinance.workers.utils.UniqueIDGeneratorWrapper
import com.bluecrunch.microfinance.workers.utils.UniqueIDGeneratorWrapperImpl
import com.cassbana.antifraud.data.*
import com.cassbana.antifraud.database.AppDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val workersModule = module {
    factory { get<AppDatabase>().simInformationDAO() }

    factory { get<Retrofit>(named(FRAUD_RETROFIT)).create(FraudApis::class.java) }

    factory <FraudRemoteDataSource> { FraudRemoteDataSourceImpl(get(), get(), ) }
    factory <FraudLocalDataSource> { FraudLocalDataSourceImpl() }
    factory { FraudRepository(get(),get()) }


    factory { SIMInformationMapper() }

    factory <UniqueIDGeneratorWrapper>{ UniqueIDGeneratorWrapperImpl(get()) }
    factory { UniqueIDGeneratorUseCase(get()) }
}