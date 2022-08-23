package com.cassbana.antifraud.workers.di



import com.cassbana.antifraud.workers.simInfo.data.mapper.RSSIMInformationMapper
import com.cassbana.antifraud.workers.utils.RSUniqueIDGeneratorUseCase
import com.cassbana.antifraud.workers.utils.RSUniqueIDGeneratorWrapper
import com.cassbana.antifraud.workers.utils.UniqueIDGeneratorWrapperImpl
import com.cassbana.antifraud.data.*
import com.cassbana.antifraud.database.RSAppDatabase
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val workersModule = module {
    factory { get<RSAppDatabase>().simInformationDAO() }

    factory { get<Retrofit>(named(FRAUD_RETROFIT)).create(RSFraudApis::class.java) }

    factory <RSFraudRemoteDataSource> { RSFraudRemoteDataSourceImpl(get(), get(), ) }
    factory <RSFraudLocalDataSource> { RSFraudLocalDataSourceImpl() }
    factory { RSFraudRepository(get(),get()) }


    factory { RSSIMInformationMapper() }

    factory <RSUniqueIDGeneratorWrapper>{ UniqueIDGeneratorWrapperImpl(get()) }
    factory { RSUniqueIDGeneratorUseCase(get()) }
}