/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package uk.co.droidinactu.myretirement.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import uk.co.droidinactu.myretirement.R
import uk.co.droidinactu.myretirement.presentation.theme.MyRetirementTheme
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // getPreferences(this)
            WearApp("Android")
        }
    }
}

@Composable
fun WearApp(greetingName: String) {
    MyRetirementTheme {
        // Hoist the list state to remember scroll position across compositions.
        val listState = rememberScalingLazyListState()
        val contentModifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)

        val events = setupList()

        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            verticalArrangement = Arrangement.Center,
            state = listState,
        ) {
            events.forEach {
                item { TextItem(contentModifier, it) }
            }
        }
    }
}

@Composable
fun TextItem(modifier: Modifier = Modifier, contents: String) {
    val shape = CircleShape
    Text(
        modifier = Modifier.fillMaxWidth()
            .padding(2.dp)
            .border(2.dp, MaterialTheme.colors.secondary, shape)
            .background(MaterialTheme.colors.background, shape)
            .padding(2.dp),
        textAlign = TextAlign.Center,
        text = contents,
    )
}

@Composable
fun setupList(): MutableList<String> {
    // TODO("Not yet implemented")

    val ageAtEvent = integerResource(R.integer.retirement_age).toLong()
    val dateOfBirth = LocalDate.of(
        integerResource(R.integer.dob_year),
        integerResource(R.integer.dob_month),
        integerResource(R.integer.dob_day),
    )

    return mutableListOf<String>(
        getEvent(Event("XMas", dateOfBirth, LocalDate.of(2023, 12, 25))),
        getEvent(Event("Holiday", dateOfBirth, LocalDate.of(2024, 1, 4))),
        getEvent(Event("Retire", dateOfBirth, dateOfBirth.plusYears(ageAtEvent), true)),
    ) // .sortedBy { it }
}

@Composable
fun getEvent(event: Event): String {
    val today = LocalDate.now()

    val ageAtEvent = Period.between(event.dob, event.date).years

    val timeToGo = Period.between(today, event.date)
    val years = timeToGo.years
    val months = timeToGo.months
    val days = timeToGo.days

    if (event.sh) {
        return stringResource(
            R.string.event_in,
            years,
            months,
            days,
            ageAtEvent,
            event.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
            event.name,
        )
    }else{
        return stringResource(
            R.string.event_in_no_age,
            years,
            months,
            days,
            event.date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
            event.name,
    }
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp("Preview Android")
}

@Composable
fun RetireIn(retireAge: Int) {
    val bday = LocalDate.of(
        integerResource(id = R.integer.dob_year),
        integerResource(R.integer.dob_month),
        integerResource(R.integer.dob_day),
    )
    val retireDate = bday.plusYears(retireAge.toLong())
    val today = LocalDate.now()

    val age = Period.between(today, retireDate)
    val years = age.years
    val months = age.months
    val days = age.days

    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 22.sp,
        color = MaterialTheme.colors.primary,
        text = stringResource(
            R.string.event_in,
            years,
            months,
            days,
            retireAge,
            retireDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
            "Retire",
        ),
    )
}

//
// fun savePreferences(activity: ComponentActivity) {
//    val sharedPreference = activity.getPreferences(Context.MODE_PRIVATE)
//    var editor = sharedPreference.edit()
//    editor.putString("dob", dateOfBirth.toString())
//    editor.putLong("ageAtEvent", ageAtEvent)
//    editor.putLong("retireAge", 100L)
//    editor.commit()
// }
//
// fun getPreferences(activity: ComponentActivity) {
//    val sharedPreference = activity.getPreferences(Context.MODE_PRIVATE)
//    sharedPreference.getString("username", "defaultName")
//    dateOfBirth = LocalDate.parse(
//        sharedPreference.getString(
//            "dob",
//            LocalDate.of(
//                activity.resources.getInteger(R.integer.dob_year),
//                activity.resources.getInteger(R.integer.dob_month),
//                activity.resources.getInteger(R.integer.dob_day),
//            ).toString(),
//        ),
//    )
//    ageAtEvent = sharedPreference.getLong(
//        "age",
//        activity.resources.getInteger(R.integer.retirement_age).toLong(),
//    )
//    sharedPreference.getLong("retireAge", 1L)
// }
