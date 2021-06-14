@file:Suppress(
    "MaximumLineLength",
    "MaxLineLength",
    "MagicNumber",
)

package com.redmadrobot.data.repository

import android.location.Address
import android.location.Geocoder
import com.redmadrobot.data.network.errors.NetworkException
import com.redmadrobot.data.network.workspace.WorkspaceApi
import com.redmadrobot.data.util.toUserProfileData
import com.redmadrobot.domain.entity.repository.Feed
import com.redmadrobot.domain.entity.repository.Post
import com.redmadrobot.domain.entity.repository.UserProfileData
import com.redmadrobot.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.random.Random

class UserDataRepositoryMockImpl @Inject constructor(
    private val api: WorkspaceApi,
    private val userProfileDataStorage: UserProfileDataStorage,
    private val mode: MODE = MODE.FULL,
    private val geocoder: Geocoder,
) : UserDataRepository {
    enum class MODE {
        EMPTY,
        FULL,
        ERROR
    }

    private val mockUser = UserProfileData(
        id = "1",
        firstName = "MockName",
        lastName = "MockLastName",
        nickname = "MockNickname",
        avatarUrl = "https://static.wikia.nocookie.net/nightinthewoods/images/0/0d/Old_cat_1_blink_00000.png/revision/latest?cb=20170810061444",
        birthDay = "2000-01-01",
    )

    var feed = mutableListOf<Post>()

    override fun initProfileData(): Flow<Unit> = flow {
        if (userProfileDataStorage.isProfileEmpty) {
            mockUserProfileData()
        }
        emit(Unit)
    }

    override fun initFeed(): Flow<Unit> = flow {
        if (mode == MODE.ERROR) {
            throw NetworkException.NoInternetAccess()
        }

        if (userProfileDataStorage.isFeedEmpty) {
            generateFullList()
            mockFeed()
        } else {
            feed = userProfileDataStorage.userFeed.first().toMutableList()
        }
        emit(Unit)
    }

    override fun updateFeed(): Flow<Unit> = flow {
        mockFeed()
        emit(Unit)
    }

    override fun updateUserProfileData(
        nickname: String,
        firstName: String,
        lastName: String,
        birthDay: String,
        avatarUrl: String?,
    ): Flow<Unit> = flow {
        val networkEntityUserProfile = api.mePatchProfile(
            nickname = nickname,
            firstName = firstName,
            lastName = lastName,
            birthday = birthDay,
            avatarFile = avatarUrl.orEmpty()
        )
        userProfileDataStorage.updateUserProfileData(networkEntityUserProfile.toUserProfileData())
        emit(Unit)
    }

    override fun getUserProfileDataFlow(): SharedFlow<UserProfileData> = userProfileDataStorage.userProfileData
    override fun getUserFeed(): SharedFlow<Feed> = userProfileDataStorage.userFeed

    override fun changeLikePost(postId: String, isLike: Boolean): Flow<Unit> = flow {
        val post = feed.find { post -> post.id == postId }

        if (post != null) {
            val postIndex = feed.indexOf(post)
            feed[postIndex] = post.copy(
                liked = isLike,
                likes = post.likes + if (isLike) 1 else -1
            )
        }

        mockFeed()
        emit(Unit)
    }

    private suspend fun mockUserProfileData() {
        userProfileDataStorage.updateUserProfileData(mockUser)
    }

    private suspend fun mockFeed() {
        userProfileDataStorage.updateFeed(feed)
    }

    private fun generateFullList() {
        for (num in 1..20) {
            val lat = Random.nextDouble(0.0, 90.0)
            val lon = Random.nextDouble(-180.0, 180.0)
            val addresses: List<Address> = geocoder.getFromLocation(lat, lon, 1)
            var location = "Там далеко-далеко"
            if (addresses.isNotEmpty() && !addresses[0].locality.isNullOrEmpty()) {
                location = addresses[0].locality
            }

            feed.add(
                Post(
                    author = mockUser.copy(id = Random.nextInt(0, 100).toString()),
                    id = num.toString(),
                    text = "It's post #$num",
                    likes = Random.nextInt(0, 100),
                    liked = Random.nextBoolean(),
                    lat = lat,
                    lon = lon,
                    location = location
                )
            )
        }
    }
}
