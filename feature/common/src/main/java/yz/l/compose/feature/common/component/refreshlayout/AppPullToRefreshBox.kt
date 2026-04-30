package yz.l.compose.feature.common.component.refreshlayout

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import timber.log.Timber
import yz.l.compose.feature.common.R

/**
 * desc:
 * created by liyuzheng on 2026/4/8 13:26
 */
@Composable
fun PagingRefreshLayout(
    modifier: Modifier = Modifier,
    cState: LazyListState = rememberLazyListState(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
    pagingItems: LazyPagingItems<*>,
    initializeLoading: @Composable BoxScope.() -> Unit = {
        LoadingPlace(
            Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
    },
    itemContent: LazyListScope.() -> Unit
) {
    var isInit by remember { mutableStateOf(true) }
    val loadState = pagingItems.loadState.refresh
    val itemCount = pagingItems.itemCount
    var showInitializeLoading by remember { mutableStateOf(false) }
    LaunchedEffect(itemCount) {
        if (itemCount == 0) {
            delay(1000)
            showInitializeLoading = true
        } else {
            showInitializeLoading = false
        }
    }

    Timber.v("PagingRefreshLayout isInit $isInit ${pagingItems.itemCount} $itemCount")
    LaunchedEffect(loadState, itemCount) {
        if (loadState is LoadState.NotLoading && itemCount > 0)
            isInit = false
    }
    AnimatedContent(
        targetState = isInit && loadState is LoadState.Loading && showInitializeLoading,
        transitionSpec = {
            (fadeIn(animationSpec = tween(2000, easing = LinearEasing)) +
                    scaleIn(
                        initialScale = 0.5f,
                        animationSpec = tween(1000, easing = LinearEasing)
                    ))
                .togetherWith(
                    fadeOut(animationSpec = tween(2000, easing = LinearEasing)) +
                            scaleOut(
                                targetScale = 0.5f,
                                animationSpec = tween(1000, easing = LinearEasing)
                            )
                )
        },
        label = "layout_switch_animation"
    ) { showLoadingPlace ->
        if (showLoadingPlace) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
            ) {
                initializeLoading()
            }
        } else {
            PagingRefreshLayoutContainer(
                cState = cState,
                modifier = modifier.fillMaxSize(),
                innerPadding = innerPadding,
                isRefreshing = loadState is LoadState.Loading && !isInit,
                pagingItems = pagingItems,
                onRefresh = { pagingItems.refresh() },
                itemContent = itemContent
            )
        }
    }
}

@Composable
fun LoadingPlace(
    modifier: Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading))
    LottieAnimation(
        composition = composition,
        iterations = LottieConstants.IterateForever, // 设置无限循环
        modifier = modifier
    )
}

@Composable
fun PagingRefreshLayoutContainer(
    modifier: Modifier,
    cState: LazyListState = rememberLazyListState(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
    isRefreshing: Boolean,
    pagingItems: LazyPagingItems<*>,
    onRefresh: () -> Unit,
    itemContent: LazyListScope.() -> Unit
) {
    val state = rememberPullToRefreshState()
    PullToRefreshBox(
        state = state,
        modifier = modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        indicator = {
            PullToRefreshDefaults.Indicator(
                state = state,
                isRefreshing = isRefreshing,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    // 这里的 padding 起到了类似“顶部 contentPadding”对指示器的作用
                    .padding(innerPadding)
            )
        }
    ) {
        PagingRefreshLayoutItems(
            state = cState,
            innerPadding = innerPadding,
            pagingItems = pagingItems,
            itemContent = itemContent
        )
    }
}

@Composable
fun PagingRefreshLayoutItems(
    state: LazyListState = rememberLazyListState(),
    innerPadding: PaddingValues = PaddingValues(0.dp),
    pagingItems: LazyPagingItems<*>,
    itemContent: LazyListScope.() -> Unit
) {
    LazyColumn(
        state = state,
        contentPadding = PaddingValues(
            0.dp,
            innerPadding.calculateTopPadding(),
            0.dp,
            100.dp
        ),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp) // 设置卡片间隔
    ) {
        itemContent()
        pagingRefreshLayoutFooter(pagingItems)
    }
}

fun LazyListScope.pagingRefreshLayoutFooter(pagingItems: LazyPagingItems<*>) {
    item(contentType = "footer_content_type") {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when (val appendState = pagingItems.loadState.append) {
                is LoadState.Loading -> {
                    if (pagingItems.itemCount <= 0) {
                        return@Box
                    }
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }

                is LoadState.Error -> {
                    Timber.v("appendState Error $appendState ")
                    // 加载失败的 UI，通常带有一个“重试”按钮
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("加载失败")
                        Button(onClick = { pagingItems.retry() }) {
                            Text("点击重试")
                        }
                    }
                }

                is LoadState.NotLoading -> {
                    Timber.v("appendState NotLoading ${appendState.endOfPaginationReached} ")
                    // 如果已全部加载完毕（endOfPaginationReached）
                    if (appendState.endOfPaginationReached) {
                        Text(
                            text = "没有更多内容了",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            color = Color.Gray
                        )
                    }

                }
            }
        }
    }
}