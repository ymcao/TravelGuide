.class public Lcom/material/travel/fragment/TravelFragment_CF;
.super Lcom/material/travel/fragment/BaseFragment;
.source "TravelFragment.java"

# interfaces
.implements Lcom/material/travel/view/IListLoadView;


# static fields
.field private static final ARG_POSITION:Ljava/lang/String; = "keyword"


# instance fields
.field private dataP:Lcom/material/travel/presenter/InitDataPresenter;

.field private isPrepared:Z

.field private itemLists:Landroid/support/v7/widget/RecyclerView;

.field private jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

.field private keyWord:Ljava/lang/String;

.field private layoutManager:Landroid/support/v7/widget/LinearLayoutManager;

.field private mAdapter:Lcom/material/travel/adapter/MainAdapter;

.field private mHasLoadedOnce:Z

.field private page:I

.field private rootView:Landroid/view/View;

.field private toTopButton:Landroid/support/design/widget/FloatingActionButton;

.field private type:I


# direct methods
.method public constructor <init>()V
    .locals 1

    .prologue
    const/4 v0, 0x1

    .line 29
    invoke-direct {p0}, Lcom/material/travel/fragment/BaseFragment;-><init>()V

    .line 43
    iput v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->type:I

    .line 44
    iput v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->page:I

    return-void
.end method

.method static synthetic access$002(Lcom/material/travel/fragment/TravelFragment;I)I
    .locals 0
    .param p0, "x0"    # Lcom/material/travel/fragment/TravelFragment;
    .param p1, "x1"    # I

    .prologue
    .line 29
    iput p1, p0, Lcom/material/travel/fragment/TravelFragment_CF;->type:I

    return p1
.end method

.method static synthetic access$102(Lcom/material/travel/fragment/TravelFragment;I)I
    .locals 0
    .param p0, "x0"    # Lcom/material/travel/fragment/TravelFragment;
    .param p1, "x1"    # I

    .prologue
    .line 29
    iput p1, p0, Lcom/material/travel/fragment/TravelFragment_CF;->page:I

    return p1
.end method

.method static synthetic access$108(Lcom/material/travel/fragment/TravelFragment;)I
    .locals 2
    .param p0, "x0"    # Lcom/material/travel/fragment/TravelFragment;

    .prologue
    .line 29
    iget v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->page:I

    add-int/lit8 v1, v0, 0x1

    iput v1, p0, Lcom/material/travel/fragment/TravelFragment_CF;->page:I

    return v0
.end method

.method static synthetic access$200(Lcom/material/travel/fragment/TravelFragment;)Landroid/support/design/widget/FloatingActionButton;
    .locals 1
    .param p0, "x0"    # Lcom/material/travel/fragment/TravelFragment;

    .prologue
    .line 29
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->toTopButton:Landroid/support/design/widget/FloatingActionButton;

    return-object v0
.end method

.method static synthetic access$300(Lcom/material/travel/fragment/TravelFragment;)V
    .locals 0
    .param p0, "x0"    # Lcom/material/travel/fragment/TravelFragment;

    .prologue
    .line 29
    invoke-direct {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->toTop()V

    return-void
.end method

.method static synthetic access$400(Lcom/material/travel/fragment/TravelFragment;)Lcom/material/mylibrary/ui/JellyRefreshLayout;
    .locals 1
    .param p0, "x0"    # Lcom/material/travel/fragment/TravelFragment;

    .prologue
    .line 29
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    return-object v0
.end method

.method private initEvent()V
    .locals 2

    .prologue
    .line 186
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->mAdapter:Lcom/material/travel/adapter/MainAdapter;

    new-instance v1, Lcom/material/travel/fragment/TravelFragment$7;

    invoke-direct {v1, p0}, Lcom/material/travel/fragment/TravelFragment$7;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    invoke-virtual {v0, v1}, Lcom/material/travel/adapter/MainAdapter;->setOnItemClickLitener(Lcom/material/travel/adapter/MainAdapter$OnItemClickLitener;)V

    .line 199
    return-void
.end method

.method public static newInstance(Ljava/lang/String;)Lcom/material/travel/fragment/TravelFragment;
    .locals 3
    .param p0, "keyWord"    # Ljava/lang/String;

    .prologue
    .line 47
    new-instance v1, Lcom/material/travel/fragment/TravelFragment;

    invoke-direct {v1}, Lcom/material/travel/fragment/TravelFragment_CF;-><init>()V

    .line 48
    .local v1, "f":Lcom/material/travel/fragment/TravelFragment;
    new-instance v0, Landroid/os/Bundle;

    invoke-direct {v0}, Landroid/os/Bundle;-><init>()V

    .line 49
    .local v0, "b":Landroid/os/Bundle;
    const-string v2, "keyword"

    invoke-virtual {v0, v2, p0}, Landroid/os/Bundle;->putString(Ljava/lang/String;Ljava/lang/String;)V

    .line 50
    invoke-virtual {v1, v0}, Lcom/material/travel/fragment/TravelFragment_CF;->setArguments(Landroid/os/Bundle;)V

    .line 51
    return-object v1
.end method

.method private toTop()V
    .locals 3
    .annotation runtime Lcom/alipay/euler/andfix/annotation/MethodReplace;
        clazz = "com.material.travel.fragment.TravelFragment"
        method = "toTop"
    .end annotation

    .prologue
    .line 206
    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    const-string v1, "PATCH_SUCCESS!"

    const/4 v2, 0x0

    invoke-static {v0, v1, v2}, Landroid/widget/Toast;->makeText(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;

    move-result-object v0

    .line 207
    invoke-virtual {v0}, Landroid/widget/Toast;->show()V

    .line 208
    return-void
.end method


# virtual methods
.method public getContext()Landroid/content/Context;
    .locals 1

    .prologue
    .line 173
    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    invoke-virtual {v0}, Landroid/support/v4/app/FragmentActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v0

    return-object v0
.end method

.method public hideLoading()V
    .locals 1

    .prologue
    .line 183
    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    check-cast v0, Lcom/material/travel/MainActivity;

    invoke-virtual {v0}, Lcom/material/travel/MainActivity;->dismissDialog()V

    .line 184
    return-void
.end method

.method protected lazyLoad()V
    .locals 3

    .prologue
    .line 165
    iget-boolean v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->isPrepared:Z

    if-eqz v0, :cond_0

    iget-boolean v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->isVisible:Z

    if-nez v0, :cond_1

    .line 169
    :cond_0
    :goto_0
    return-void

    .line 168
    :cond_1
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->dataP:Lcom/material/travel/presenter/InitDataPresenter;

    iget-object v1, p0, Lcom/material/travel/fragment/TravelFragment_CF;->keyWord:Ljava/lang/String;

    iget v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->page:I

    invoke-virtual {v0, v1, v2}, Lcom/material/travel/presenter/InitDataPresenter;->initMainData(Ljava/lang/String;I)V

    goto :goto_0
.end method

.method public onCreateView(Landroid/view/LayoutInflater;Landroid/view/ViewGroup;Landroid/os/Bundle;)Landroid/view/View;
    .locals 4
    .param p1, "inflater"    # Landroid/view/LayoutInflater;
    .param p2, "container"    # Landroid/view/ViewGroup;
    .param p3, "savedInstanceState"    # Landroid/os/Bundle;

    .prologue
    const/4 v3, 0x1

    .line 56
    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getArguments()Landroid/os/Bundle;

    move-result-object v0

    const-string v1, "keyword"

    invoke-virtual {v0, v1}, Landroid/os/Bundle;->getString(Ljava/lang/String;)Ljava/lang/String;

    move-result-object v0

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->keyWord:Ljava/lang/String;

    .line 58
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->rootView:Landroid/view/View;

    if-nez v0, :cond_0

    .line 59
    const v0, 0x7f040026

    const/4 v1, 0x0

    invoke-virtual {p1, v0, p2, v1}, Landroid/view/LayoutInflater;->inflate(ILandroid/view/ViewGroup;Z)Landroid/view/View;

    move-result-object v0

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->rootView:Landroid/view/View;

    .line 60
    :cond_0
    new-instance v0, Landroid/support/v7/widget/LinearLayoutManager;

    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v1

    invoke-direct {v0, v1}, Landroid/support/v7/widget/LinearLayoutManager;-><init>(Landroid/content/Context;)V

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->layoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    .line 61
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->rootView:Landroid/view/View;

    const v1, 0x7f0c0095

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Lcom/material/mylibrary/ui/JellyRefreshLayout;

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    .line 62
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    new-instance v1, Lcom/material/travel/fragment/TravelFragment$1;

    invoke-direct {v1, p0}, Lcom/material/travel/fragment/TravelFragment$1;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    invoke-virtual {v0, v1}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->setRefreshListener(Lcom/material/mylibrary/ui/JellyRefreshLayout$JellyRefreshListener;)V

    .line 78
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->rootView:Landroid/view/View;

    const v1, 0x7f0c0096

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/support/v7/widget/RecyclerView;

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->itemLists:Landroid/support/v7/widget/RecyclerView;

    .line 79
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->itemLists:Landroid/support/v7/widget/RecyclerView;

    iget-object v1, p0, Lcom/material/travel/fragment/TravelFragment_CF;->layoutManager:Landroid/support/v7/widget/LinearLayoutManager;

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->setLayoutManager(Landroid/support/v7/widget/RecyclerView$LayoutManager;)V

    .line 80
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->itemLists:Landroid/support/v7/widget/RecyclerView;

    new-instance v1, Lcom/material/mylibrary/ui/DividerItemDecoration;

    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v2

    invoke-direct {v1, v2, v3, v3}, Lcom/material/mylibrary/ui/DividerItemDecoration;-><init>(Landroid/content/Context;IZ)V

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->addItemDecoration(Landroid/support/v7/widget/RecyclerView$ItemDecoration;)V

    .line 82
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->itemLists:Landroid/support/v7/widget/RecyclerView;

    new-instance v1, Landroid/support/v7/widget/DefaultItemAnimator;

    invoke-direct {v1}, Landroid/support/v7/widget/DefaultItemAnimator;-><init>()V

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->setItemAnimator(Landroid/support/v7/widget/RecyclerView$ItemAnimator;)V

    .line 83
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->itemLists:Landroid/support/v7/widget/RecyclerView;

    new-instance v1, Lcom/material/travel/fragment/TravelFragment$2;

    invoke-direct {v1, p0}, Lcom/material/travel/fragment/TravelFragment$2;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    invoke-virtual {v0, v1}, Landroid/support/v7/widget/RecyclerView;->addOnScrollListener(Landroid/support/v7/widget/RecyclerView$OnScrollListener;)V

    .line 95
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->rootView:Landroid/view/View;

    const v1, 0x7f0c0097

    invoke-virtual {v0, v1}, Landroid/view/View;->findViewById(I)Landroid/view/View;

    move-result-object v0

    check-cast v0, Landroid/support/design/widget/FloatingActionButton;

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->toTopButton:Landroid/support/design/widget/FloatingActionButton;

    .line 96
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->toTopButton:Landroid/support/design/widget/FloatingActionButton;

    new-instance v1, Lcom/material/travel/fragment/TravelFragment$3;

    invoke-direct {v1, p0}, Lcom/material/travel/fragment/TravelFragment$3;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    invoke-virtual {v0, v1}, Landroid/support/design/widget/FloatingActionButton;->setOnClickListener(Landroid/view/View$OnClickListener;)V

    .line 102
    new-instance v0, Lcom/material/travel/presenter/InitDataPresenter;

    invoke-direct {v0, p0}, Lcom/material/travel/presenter/InitDataPresenter;-><init>(Lcom/material/travel/view/IListLoadView;)V

    iput-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->dataP:Lcom/material/travel/presenter/InitDataPresenter;

    .line 103
    iput-boolean v3, p0, Lcom/material/travel/fragment/TravelFragment_CF;->isPrepared:Z

    .line 104
    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->lazyLoad()V

    .line 105
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->rootView:Landroid/view/View;

    return-object v0
.end method

.method public onDestroy()V
    .locals 0

    .prologue
    .line 203
    invoke-super {p0}, Lcom/material/travel/fragment/BaseFragment;->onDestroy()V

    .line 204
    return-void
.end method

.method public onError(Ljava/lang/String;)V
    .locals 4
    .param p1, "msg"    # Ljava/lang/String;

    .prologue
    .line 144
    iget v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->type:I

    const/4 v1, 0x1

    if-ne v0, v1, :cond_0

    .line 145
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    new-instance v1, Lcom/material/travel/fragment/TravelFragment$5;

    invoke-direct {v1, p0}, Lcom/material/travel/fragment/TravelFragment$5;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    const-wide/16 v2, 0x1f4

    invoke-virtual {v0, v1, v2, v3}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 161
    :goto_0
    return-void

    .line 153
    :cond_0
    iget-object v0, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    new-instance v1, Lcom/material/travel/fragment/TravelFragment$6;

    invoke-direct {v1, p0}, Lcom/material/travel/fragment/TravelFragment$6;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    const-wide/16 v2, 0x1388

    invoke-virtual {v0, v1, v2, v3}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->postDelayed(Ljava/lang/Runnable;J)Z

    goto :goto_0
.end method

.method public setTravelInfo(Lcom/material/travel/model/TravelInfo;)V
    .locals 7
    .param p1, "info"    # Lcom/material/travel/model/TravelInfo;

    .prologue
    const/4 v6, 0x1

    .line 111
    iget-object v2, p1, Lcom/material/travel/model/TravelInfo;->data:Lcom/material/travel/model/TravelInfo$Data;

    iget v1, v2, Lcom/material/travel/model/TravelInfo$Data;->count:I

    .line 112
    .local v1, "total":I
    iget v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->page:I

    mul-int/lit8 v0, v2, 0xa

    .line 114
    .local v0, "hasLoadedNum":I
    iget-boolean v2, p1, Lcom/material/travel/model/TravelInfo;->ret:Z

    if-eqz v2, :cond_0

    iget-object v2, p1, Lcom/material/travel/model/TravelInfo;->data:Lcom/material/travel/model/TravelInfo$Data;

    iget-object v2, v2, Lcom/material/travel/model/TravelInfo$Data;->books:Ljava/util/List;

    invoke-interface {v2}, Ljava/util/List;->size()I

    move-result v2

    if-lez v2, :cond_0

    .line 116
    iget v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->type:I

    if-ne v2, v6, :cond_1

    .line 117
    iget-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    new-instance v3, Lcom/material/travel/fragment/TravelFragment$4;

    invoke-direct {v3, p0}, Lcom/material/travel/fragment/TravelFragment$4;-><init>(Lcom/material/travel/fragment/TravelFragment;)V

    const-wide/16 v4, 0x3e8

    invoke-virtual {v2, v3, v4, v5}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->postDelayed(Ljava/lang/Runnable;J)Z

    .line 124
    new-instance v2, Lcom/material/travel/adapter/MainAdapter;

    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v3

    invoke-virtual {v3}, Landroid/support/v4/app/FragmentActivity;->getApplicationContext()Landroid/content/Context;

    move-result-object v3

    iget-object v4, p1, Lcom/material/travel/model/TravelInfo;->data:Lcom/material/travel/model/TravelInfo$Data;

    iget-object v4, v4, Lcom/material/travel/model/TravelInfo$Data;->books:Ljava/util/List;

    invoke-direct {v2, v3, v4}, Lcom/material/travel/adapter/MainAdapter;-><init>(Landroid/content/Context;Ljava/util/List;)V

    iput-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->mAdapter:Lcom/material/travel/adapter/MainAdapter;

    .line 125
    iget-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->itemLists:Landroid/support/v7/widget/RecyclerView;

    iget-object v3, p0, Lcom/material/travel/fragment/TravelFragment_CF;->mAdapter:Lcom/material/travel/adapter/MainAdapter;

    invoke-virtual {v2, v3}, Landroid/support/v7/widget/RecyclerView;->setAdapter(Landroid/support/v7/widget/RecyclerView$Adapter;)V

    .line 132
    :goto_0
    if-ge v0, v1, :cond_2

    .line 133
    iget-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    invoke-virtual {v2, v6}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->setHashNext(Z)V

    .line 138
    :goto_1
    invoke-direct {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->initEvent()V

    .line 140
    :cond_0
    return-void

    .line 129
    :cond_1
    iget-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    invoke-virtual {v2}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->finishLoadMore()V

    .line 130
    iget-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->mAdapter:Lcom/material/travel/adapter/MainAdapter;

    iget-object v3, p1, Lcom/material/travel/model/TravelInfo;->data:Lcom/material/travel/model/TravelInfo$Data;

    iget-object v3, v3, Lcom/material/travel/model/TravelInfo$Data;->books:Ljava/util/List;

    invoke-virtual {v2, v3}, Lcom/material/travel/adapter/MainAdapter;->add(Ljava/util/List;)V

    goto :goto_0

    .line 135
    :cond_2
    iget-object v2, p0, Lcom/material/travel/fragment/TravelFragment_CF;->jelly_refresh:Lcom/material/mylibrary/ui/JellyRefreshLayout;

    const/4 v3, 0x0

    invoke-virtual {v2, v3}, Lcom/material/mylibrary/ui/JellyRefreshLayout;->setHashNext(Z)V

    goto :goto_1
.end method

.method public showLoading()V
    .locals 1

    .prologue
    .line 178
    invoke-virtual {p0}, Lcom/material/travel/fragment/TravelFragment_CF;->getActivity()Landroid/support/v4/app/FragmentActivity;

    move-result-object v0

    check-cast v0, Lcom/material/travel/MainActivity;

    invoke-virtual {v0}, Lcom/material/travel/MainActivity;->showDialog()V

    .line 179
    return-void
.end method
