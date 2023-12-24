package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import android.graphics.drawable.Drawable
import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfoDomainModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventParticipantDetailPresentationModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailBasicPresentationModel
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventInfoPresentationModel
import com.charmflex.sportgether.sdk.profile.internal.domain.UserProfileIconType
import javax.inject.Inject

internal class EventDetailPresentationModelMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) : Mapper<EventInfoDomainModel, List<EventInfoPresentationModel>> {

    override fun map(from: EventInfoDomainModel): List<EventInfoPresentationModel> {
        return listOf(
            EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_name),
                value = from.eventName
            ),
            EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_place),
                value = from.place
            ),
            EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                value = from.startTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                value = from.endTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                value = from.maxParticipantCount.toString()
            ),
            EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_description),
                value = from.description
            ),
            if (from.joiners.isNotEmpty()) EventParticipantDetailPresentationModel(
                name = resourcesProvider.getString(R.string.event_participants),
                joinedCount = from.joiners.size,
                icons = from.joiners.mapIndexedNotNull { index, info ->
                    if (index > 6) return@mapIndexedNotNull null
                    getIcon(info.profileIconName)
                },
                maxAvailableCount = from.maxParticipantCount
            ) else EventDetailBasicPresentationModel(
                name = resourcesProvider.getString(R.string.event_description),
                value = resourcesProvider.getString(R.string.event_detail_no_participant_yet)
            )
        )
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