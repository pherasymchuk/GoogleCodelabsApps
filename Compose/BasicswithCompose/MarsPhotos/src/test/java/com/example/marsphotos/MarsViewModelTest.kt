package com.example.marsphotos

import com.example.marsphotos.fake.FakeDataSource
import com.example.marsphotos.fake.FakeMarsPhotosRepository
import com.example.marsphotos.rules.ChangeMainDispatcherRule
import com.example.marsphotos.ui.screens.MarsUiState
import com.example.marsphotos.ui.screens.MarsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ChangeMainDispatcherRule::class)
class MarsViewModelTest {

    @Test
    fun marsViewModel_getMarsPhotos_verifyMarsUiStateSuccess() = runTest {
        val marsViewModel = MarsViewModel(
            marsPhotosRepository = FakeMarsPhotosRepository()
        )
        Assertions.assertEquals(
            MarsUiState.Success(
                FakeDataSource.photosList
            ),
            marsViewModel.marsUiState
        )
    }
}
