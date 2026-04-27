package yz.l.compose.home.impl

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import timber.log.Timber
import yz.l.compose.feature.common.component.AppTopBar
import yz.l.compose.feature.common.component.refreshlayout.PagingRefreshLayout

/**
 * desc:
 * created by liyuzheng on 2026/4/25 16:35
 */

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel()) {
    Timber.v("DiscoverScreen compose")
    Scaffold(
        topBar = {
            AppTopBar("首页")
        },
        content = { innerPadding ->
            val pagingItems = viewModel.fetchHomeItems().collectAsLazyPagingItems()
            PagingRefreshLayout(
                modifier = Modifier.padding(horizontal = 24.dp),
                innerPadding = innerPadding,
                pagingItems = pagingItems
            ) {
                items(
                    count = pagingItems.itemCount,
                    key = pagingItems.itemKey { it.feedId }

                ) { index ->
                    val item = pagingItems[index]
                    Text(
                        text = item?.feedId.toString(),
                        fontSize = 12.sp,
                        lineHeight = 12.sp,
                        modifier = Modifier.padding(16.dp, 0.dp)
                    )
                }
            }
        })
}