package com.charmflex.sportgether.sdk.events.internal.event.domain.mapper

import android.graphics.drawable.Drawable
import com.charmflex.sportgether.sdk.core.utils.DEFAULT_DATE_TIME_PATTERN
import com.charmflex.sportgether.sdk.core.utils.Mapper
import com.charmflex.sportgether.sdk.core.utils.ResourcesProvider
import com.charmflex.sportgether.sdk.core.utils.fromISOToStringWithPattern
import com.charmflex.sportgether.sdk.events.R
import com.charmflex.sportgether.sdk.events.internal.event.domain.models.EventInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailContentInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailParticipantsInfo
import com.charmflex.sportgether.sdk.events.internal.event.ui.event_details.EventDetailTwoLineInfo
import com.charmflex.sportgether.sdk.profile.internal.domain.UserProfileIconType
import javax.inject.Inject

internal class EventDetailFieldInfoMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) : Mapper<EventInfo, List<EventDetailContentInfo>> {

    override fun map(from: EventInfo): List<EventDetailContentInfo> {
        return listOf(
            EventDetailTwoLineInfo(
                name = resourcesProvider.getString(R.string.event_detail_name),
                value = from.eventName
            ),
            EventDetailTwoLineInfo(
                name = resourcesProvider.getString(R.string.event_detail_place),
                value = from.place
            ),
            EventDetailTwoLineInfo(
                name = resourcesProvider.getString(R.string.event_detail_start_time),
                value = from.startTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailTwoLineInfo(
                name = resourcesProvider.getString(R.string.event_detail_end_time),
                value = from.endTime.fromISOToStringWithPattern(DEFAULT_DATE_TIME_PATTERN)
            ),
            EventDetailTwoLineInfo(
                name = resourcesProvider.getString(R.string.event_max_joiner_num),
                value = from.maxParticipantCount.toString()
            ),
            EventDetailTwoLineInfo(
                name = resourcesProvider.getString(R.string.event_description),
                value = from.description
            ),
            EventDetailParticipantsInfo(
                name = resourcesProvider.getString(R.string.event_participants),
                icons = from.joiners.mapNotNull { info ->
                    getIcon(info.profileIconName)
                }
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