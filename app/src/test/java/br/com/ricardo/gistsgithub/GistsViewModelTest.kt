package br.com.ricardo.gistsgithub

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.ricardo.gistsgithub.data.GistsListResult
import br.com.ricardo.gistsgithub.data.model.Gist
import br.com.ricardo.gistsgithub.data.model.OwnerInfo
import br.com.ricardo.gistsgithub.presentation.gistslist.viewmodel.GistsViewModel
import br.com.ricardo.gistsgithub.utils.MockRepository
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GistsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: GistsViewModel

    private val dispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var gistsLiveDataObserver: Observer<List<Gist>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>



    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `call getGistsList with liveData success`() {

        val gist = listOf(
            Gist("loren ipsun loren ipsun", OwnerInfo("Ricardo", "http://..."))
        )
        val resultSuccess = MockRepository(GistsListResult.Success(gist))

        viewModel = GistsViewModel(resultSuccess)
        viewModel.gistsList.observeForever(gistsLiveDataObserver)
        viewModel.viewFlipper.observeForever(viewFlipperLiveDataObserver)


        viewModel.getGistsList(1)

        verify(gistsLiveDataObserver).onChanged(gist)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `call getGistsList with liveData empty`() {
        val resultSuccess = MockRepository(GistsListResult.Success(listOf()))

        viewModel = GistsViewModel(resultSuccess)
        viewModel.gistsList.observeForever(gistsLiveDataObserver)
        viewModel.viewFlipper.observeForever(viewFlipperLiveDataObserver)

        viewModel.getGistsList(1)

        verify(gistsLiveDataObserver).onChanged(listOf())
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `call getGistsList with liveData api error`() {
        val statusCode = 422
        val message = R.string.gists_view_flipper_error_422
        val resultApiError = MockRepository(GistsListResult.ApiError(statusCode))

        viewModel = GistsViewModel(resultApiError)
        viewModel.viewFlipper.observeForever(viewFlipperLiveDataObserver)

        viewModel.getGistsList(1)

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, message))
    }

    @Test
    fun `call getGistsList with liveData server error`() {
        val statusCode = 500
        val message = R.string.gists_view_flipper_error_500
        val resultApiError = MockRepository(GistsListResult.ApiError(statusCode))

        viewModel = GistsViewModel(resultApiError)
        viewModel.viewFlipper.observeForever(viewFlipperLiveDataObserver)

        viewModel.getGistsList(1)

        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, message))
    }
}