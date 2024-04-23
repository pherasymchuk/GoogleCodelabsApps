package com.example.amphibians.ui.screens

import com.example.amphibians.fakes.FakeAmphibiansRepository
import com.example.amphibians.fakes.FakeDatasource
import com.github.kotlinutils.junitRules.ChangeMainDispatcherRule
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(ChangeMainDispatcherRule::class)
class HomeViewModelTest {
    @Test
    fun homeViewModel_getAmphibians_updatesUiStateCorrectly() {
        val homeViewModel: HomeViewModel = HomeViewModel(FakeAmphibiansRepository())
        Assertions.assertEquals(
            AmphibiansUiState.Success(FakeDatasource.fakeAmphibians),
            homeViewModel.amphibiansUiState
        )
    }
}
