package imaavalenzuela.proofofworkout.viewmodel

import org.junit.Test

class WorkoutViewModelTest {

    @Test
    fun `getWorkouts initial state`() {
        // Verify that getWorkouts() LiveData is not null and its initial value is an empty list after the ViewModel is initialized and before the repository returns data.
        // TODO implement test
    }

    @Test
    fun `getWorkouts after load`() {
        // Verify that getWorkouts() LiveData holds the correct list of WorkoutSessions after loadWorkouts() is called.
        // TODO implement test
    }

    @Test
    fun `getCurrentExercises initial state`() {
        // Verify that getCurrentExercises() LiveData is initialized with an empty list.
        // TODO implement test
    }

    @Test
    fun `getSelectedSession initial state`() {
        // Verify that getSelectedSession() LiveData is initialized with a null value.
        // TODO implement test
    }

    @Test
    fun `getAchievements initial state`() {
        // Verify that getAchievements() LiveData correctly reflects the achievements loaded from the repository upon ViewModel initialization.
        // TODO implement test
    }

    @Test
    fun `loadWorkouts with data`() {
        // Test that calling loadWorkouts() updates the workouts LiveData with the list of sessions provided by the repository.
        // TODO implement test
    }

    @Test
    fun `loadWorkouts with empty repository`() {
        // Test that calling loadWorkouts() correctly updates the workouts LiveData to an empty list when the repository has no workouts.
        // TODO implement test
    }

    @Test
    fun `selectWorkout with valid ID`() {
        // Verify that when selectWorkout() is called with a valid ID, _selectedSession is updated with the correct WorkoutSession and _currentExercises is populated with the session's exercises.
        // TODO implement test
    }

    @Test
    fun `selectWorkout with invalid ID`() {
        // Test that calling selectWorkout() with a non-existent ID results in _selectedSession becoming null and _currentExercises being cleared (set to an empty list).
        // TODO implement test
    }

    @Test
    fun `selectWorkout with session containing no exercises`() {
        // Verify that selecting a workout with an empty or null exercise list results in _currentExercises being an empty list.
        // TODO implement test
    }

    @Test
    fun `addExercise to empty list`() {
        // Verify that adding an exercise when the current list is empty correctly updates the _currentExercises LiveData to contain just that one exercise.
        // TODO implement test
    }

    @Test
    fun `addExercise to existing list`() {
        // Verify that adding an exercise to a non-empty list appends it to the end and correctly updates the LiveData.
        // TODO implement test
    }

    @Test
    fun `addExercise with null exercise object`() {
        // Test the behavior of adding a null exercise object to the list, expecting it to be added without crashing.
        // TODO implement test
    }

    @Test
    fun `updateExercise with valid index`() {
        // Test that providing a valid index and an updated exercise object correctly replaces the exercise at that position in the _currentExercises list.
        // TODO implement test
    }

    @Test
    fun `updateExercise with out of bounds index`() {
        // Test that attempting to update an exercise with an out-of-bounds index (e.g., -1 or size()) does not modify the list and throws an IndexOutOfBoundsException.
        // TODO implement test
    }

    @Test
    fun `updateExercise on empty list`() {
        // Verify that calling updateExercise when the list is empty does not cause a crash and the list remains empty.
        // TODO implement test
    }

    @Test
    fun `deleteExercise with existing exercise`() {
        // Verify that calling deleteExercise with an exercise object that exists in the list removes it and updates the LiveData.
        // TODO implement test
    }

    @Test
    fun `deleteExercise with non existent exercise`() {
        // Test that calling deleteExercise with an exercise object that is not in the list does not change the list or cause an error.
        // TODO implement test
    }

    @Test
    fun `deleteExercise on an empty list`() {
        // Verify that calling deleteExercise when the list of exercises is empty does not cause a crash.
        // TODO implement test
    }

    @Test
    fun `saveWorkout as new workout`() {
        // Test that saveWorkout() with a null existingId calls addWorkout on the repository with a new WorkoutSession.
        // Also verify that _currentExercises is cleared and loadWorkouts() is called.
        // TODO implement test
    }

    @Test
    fun `saveWorkout to update existing workout`() {
        // Test that saveWorkout() with a valid existingId calls updateWorkout on the repository with the correct updated session data.
        // Also verify that _currentExercises is cleared and loadWorkouts() is called.
        // TODO implement test
    }

    @Test
    fun `saveWorkout with empty exercise list`() {
        // Verify that saving a workout with no exercises in _currentExercises correctly creates or updates a session with an empty exercise list.
        // TODO implement test
    }

    @Test
    fun `saveWorkout with empty name`() {
        // Test that saving a workout with an empty string for a name correctly creates or updates the session with an empty name.
        // TODO implement test
    }

    @Test
    fun `saveWorkout triggers achievement check`() {
        // Verify that saveWorkout() successfully triggers the checkAchievements() method after saving.
        // TODO implement test
    }

    @Test
    fun `deleteWorkout with valid ID`() {
        // Test that deleteWorkout() calls the repository's delete method with the correct ID and subsequently reloads the workouts by calling loadWorkouts().
        // TODO implement test
    }

    @Test
    fun `deleteWorkout with invalid ID`() {
        // Verify that calling deleteWorkout() with a non-existent ID still triggers a reload and does not crash the app, leaving the workout list as is.
        // TODO implement test
    }

    @Test
    fun `clearExercises on non empty list`() {
        // Verify that clearExercises() updates the _currentExercises LiveData to an empty list when it previously contained exercises.
        // TODO implement test
    }

    @Test
    fun `clearExercises on already empty list`() {
        // Test that calling clearExercises() when the list is already empty does not cause any issues and the list remains empty.
        // TODO implement test
    }

}
