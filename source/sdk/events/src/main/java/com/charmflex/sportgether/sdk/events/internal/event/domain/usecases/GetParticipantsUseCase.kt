package com.charmflex.sportgether.sdk.events.internal.event.domain.usecases

import android.graphics.drawable.Drawable
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.events.EventService
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.ParticipantsData
import com.charmflex.sportgether.sdk.profile.internal.domain.UserProfileIconType
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class GetParticipantsUseCase @Inject constructor(
    private val eventService: EventService,
    private val resourcesProvider: ResourcesProvider
) {

    suspend operator fun invoke(eventId: Int): Result<List<ParticipantsData>> {
        return eventService.fetchEvents().first().map {
            val event = it.eventInfo.first { event -> event.eventId == eventId }
            event.joiners.map { pInfo ->
                ParticipantsData(
                    id = pInfo.userId,
                    name = pInfo.username,
                    icon = getIcon(pInfo.profileIconName)
                )
            }
        }
    }

    private fun getIcon(type: String): Drawable? {
        return when (type) {
            UserProfileIconType.CHINESE_BOY.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.chinese_boy_icon)
            UserProfileIconType.CHINESE_GIRL.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.chinese_girl_icon)
            UserProfileIconType.INDIAN_BOY.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.idian_boy_icon)
            UserProfileIconType.INDIAN_GIRL.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.indian_girl_icon)
            UserProfileIconType.MALAY_BOY.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.malay_boy_icon)
            UserProfileIconType.MALAY_GIRL.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.malay_girl_icon)
            UserProfileIconType.BALD_UNCLE.toString() -> resourcesProvider.getDrawable(com.charmflex.sportgether.sdk.ui_common.R.drawable.bald_chinese_uncle_icon)
            UserProfileIconType.FOREIGNER_SHORT_HAIR_GIRL.toString() -> resourcesProvider.getDrawable(
                com.charmflex.sportgether.sdk.ui_common.R.drawable.foreigner_short_hair_girl_icon
            )

            UserProfileIconType.FOREIGNER_LONG_HAIR_GIRL.toString() -> resourcesProvider.getDrawable(
                com.charmflex.sportgether.sdk.ui_common.R.drawable.foreigner_girl
            )

            else -> null
        }
    }
}