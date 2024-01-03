/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package uk.co.droidinactu.myretirement.presentation

import android.content.Context
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.rememberScalingLazyListState
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import uk.co.droidinactu.myretirement.R
import uk.co.droidinactu.myretirement.presentation.theme.MyRetirementTheme
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

var events: MutableList<Event> = mutableListOf()
var eventsStrings: MutableList<String> = mutableListOf()
var dateOfBirth = LocalDate.now()

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      getPreferences(this)
      WearApp()
    }
  }
}

@Composable
fun WearApp() {
  MyRetirementTheme {
    eventsStrings = setupStringList()
    setupDisplay(eventsStrings)
  }
}

@Composable
fun setupDisplay(events: MutableList<String>) {
  // Hoist the list state to remember scroll position across compositions.
  val listState = rememberScalingLazyListState()
  val contentModifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
  ScalingLazyColumn(
    modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
    verticalArrangement = Arrangement.Center,
    state = listState,
  ) {
    events.forEach {
      item { TextItem(contentModifier, it) }
    }
    item {
      addEventButton(onClick = {
        // showAddEvent()
      })
    }
    item {
      addSettingsButton(onClick = {
        // showAddEvent()
      })
    }
  }
}

@Composable
fun addEventButton(onClick: () -> Unit) {
  Button(
    modifier = Modifier.fillMaxWidth().padding(6.dp)
      .background(MaterialTheme.colors.background).padding(3.dp),
    onClick = { onClick() },
  ) {
    Text("Add Event")
  }
}

@Composable
fun addSettingsButton(onClick: () -> Unit) {
  Button(
    modifier = Modifier.fillMaxWidth().padding(6.dp)
      .background(MaterialTheme.colors.background).padding(3.dp),
    onClick = { onClick() },
  ) {
    Text("Settings")
  }
}

@Composable
fun showAddEvent() {
}

// @Composable
// fun CardMinimalExample() {
//    Card() {
//        Text(text = "Hello, world!")
//    }
// }

@Composable
fun TextItem(modifier: Modifier = Modifier, contents: String) {
  val shape = CircleShape
  Text(
    modifier = Modifier.fillMaxWidth().padding(4.dp)
      .border(2.dp, MaterialTheme.colors.secondary, shape)
      .background(MaterialTheme.colors.background, shape).padding(8.dp),
    textAlign = TextAlign.Center,
    text = contents,
  )
}

@Composable
fun setupStringList(): MutableList<String> {
  // TODO("Not yet implemented")
  setupList()
  var eventStrings = mutableListOf<String>()
  events.forEach { it -> eventStrings.add(getEvent(event = it)) }
  return eventStrings
}

@Composable
fun setupList() {
  // TODO("Not yet implemented")

    var eventList = mutableListOf<Event>(
        Event("XMas", dateOfBirth, LocalDate.of(2024, 12, 25)),
        Event("XMas", dateOfBirth, LocalDate.of(2023, 12, 25)),
        Event("Birthday", dateOfBirth, LocalDate.of(2024, 3, 27)),
    ).sortedWith(compareBy({ it.date }))

    var events = mutableListOf<String>()
    eventList.forEach { it -> events.add(getEvent(event = it)) }
    return events
  events = mutableListOf<Event>(
    Event(name = "XMas", date = LocalDate.of(2024, 12, 25)),
  ).sortedWith(compareBy { it.date }).toMutableList()
  //TODO("display message to say new list created") Toast.makeText()
}

@Composable
fun getEvent(event: Event): String {
  val today = LocalDate.now()
  val ageAtEvent = event.ageAtEvent ?: Period.between(dateOfBirth, event.date).years
  val date = event.date ?: calcDateAtAge(event.dob!!, ageAtEvent)

  val timeToGo = Period.between(today, date)
  val years = timeToGo.years
  val months = timeToGo.months
  val days = timeToGo.days

  if (event.showAge) {
    return stringResource(
      R.string.event_in,
      years,
      months,
      days,
      ageAtEvent,
      date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
      event.name,
    )
  }
  return stringResource(
    R.string.event_in_no_age,
    years,
    months,
    days,
    date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")),
    event.name,
  )
}

fun calcDateAtAge(dob: LocalDate, ageAtEvent: Int): LocalDate = dob.plusYears(ageAtEvent.toLong())


@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
  WearApp()
}

fun savePreferences(activity: ComponentActivity) {
  // TODO("Not yet implemented")
  val sharedPreference = activity.getPreferences(Context.MODE_PRIVATE)
  var editor = sharedPreference.edit()
  for (event in events) {
    editor.putString("name", event.name)
    editor.putString("date", event.date.toString())
    editor.putString("dob", event.date.toString())
    editor.putInt("ageAtEvent", event.ageAtEvent ?: 0)
    editor.putBoolean("showAge", event.showAge)
  }
  editor.apply()
}

fun getPreferences(activity: ComponentActivity) {
  // TODO("Not yet implemented")
  val sharedPreference = activity.getPreferences(Context.MODE_PRIVATE)
  sharedPreference.getString("username", "defaultName")
  dateOfBirth = LocalDate.parse(
    sharedPreference.getString(
      "dob",
      LocalDate.of(2000, 1, 1).toString(),
    ),
  )
}
