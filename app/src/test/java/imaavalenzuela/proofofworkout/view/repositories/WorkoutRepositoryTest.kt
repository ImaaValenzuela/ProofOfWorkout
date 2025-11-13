package imaavalenzuela.proofofworkout.view.repositories

import org.junit.Test

class WorkoutRepositoryTest {

    @Test
    fun `getAllWorkouts with no stored workouts`() {
        // When SharedPreferences is empty or the 'workout_list' key doesn't exist, verify that getAllWorkouts() returns an empty mutable list.
        // TODO implement test
    }

    @Test
    fun `getAllWorkouts with existing workouts`() {
        // When SharedPreferences contains a valid JSON string of workout sessions, verify that getAllWorkouts() correctly deserializes it and returns the expected list of WorkoutSession objects.
        // TODO implement test
    }

    @Test
    fun `getAllWorkouts with malformed JSON data`() {
        // If the data stored in SharedPreferences under 'workout_list' is corrupted or not valid JSON, ensure the method handles the deserialization error gracefully, ideally returning an empty list.
        // TODO implement test
    }

    @Test
    fun `getWorkoutById with a valid and existing ID`() {
        // When a valid ID that exists in the stored list is provided, verify the method returns the correct WorkoutSession object.
        // TODO implement test
    }

    @Test
    fun `getWorkoutById with a non existent ID`() {
        // When an ID that does not exist in the list is provided, verify the method returns null.
        // TODO implement test
    }

    @Test
    fun `getWorkoutById from an empty workout list`() {
        // When the stored workout list is empty, verify that calling getWorkoutById with any ID returns null.
        // TODO implement test
    }

    @Test
    fun `getWorkoutById with a negative ID`() {
        // Test retrieving a workout using a negative ID, assuming workout IDs can be negative, to ensure it returns the correct object or null if not found.
        // TODO implement test
    }

    @Test
    fun `saveWorkouts with a non empty list`() {
        // Verify that saving a list of WorkoutSession objects correctly serializes them to a JSON string and saves it to SharedPreferences under the 'workout_list' key.
        // TODO implement test
    }

    @Test
    fun `saveWorkouts with an empty list`() {
        // Verify that saving an empty list correctly writes an empty JSON array '[]' to SharedPreferences, effectively clearing the stored workouts.
        // TODO implement test
    }

    @Test
    fun `saveWorkouts overwriting existing data`() {
        // First, save an initial list of workouts. Then, save a new, different list. Verify that the original list is completely replaced by the new one.
        // TODO implement test
    }

    @Test
    fun `addWorkout to an empty list`() {
        // When the repository is empty, verify that adding a new WorkoutSession results in a list containing only that session.
        // TODO implement test
    }

    @Test
    fun `addWorkout to a non empty list`() {
        // When the repository already contains workouts, verify that adding a new WorkoutSession appends it to the end of the existing list.
        // TODO implement test
    }

    @Test
    fun `addWorkout with a duplicate ID`() {
        // Verify that adding a WorkoutSession with an ID that already exists in the list successfully adds the duplicate, as the current implementation does not prevent it.
        // TODO implement test
    }

    @Test
    fun `updateWorkout with an existing ID`() {
        // Provide an updated WorkoutSession with an ID that exists in the list. Verify that the corresponding item in the list is replaced with the new data.
        // TODO implement test
    }

    @Test
    fun `updateWorkout with a non existent ID`() {
        // Attempt to update a workout using a WorkoutSession object whose ID does not exist in the stored list. Verify that the list remains unchanged.
        // TODO implement test
    }

    @Test
    fun `updateWorkout on an empty list`() {
        // Attempt to update a workout when no workouts are stored. Verify that the list remains empty and no errors occur.
        // TODO implement test
    }

    @Test
    fun `deleteWorkout with an existing ID`() {
        // Call deleteWorkout with an ID that exists in the list. Verify that the specific WorkoutSession is removed and all other sessions remain.
        // TODO implement test
    }

    @Test
    fun `deleteWorkout with a non existent ID`() {
        // Call deleteWorkout with an ID that does not exist in the list. Verify that the list remains unchanged.
        // TODO implement test
    }

    @Test
    fun `deleteWorkout on an empty list`() {
        // Call deleteWorkout on an empty repository. Verify that the app does not crash and the list remains empty.
        // TODO implement test
    }

    @Test
    fun `deleteWorkout last remaining item`() {
        // When there is only one workout in the list, delete it and verify that the resulting list is empty.
        // TODO implement test
    }

    @Test
    fun `deleteWorkout with duplicate IDs`() {
        // If the list contains multiple workouts with the same ID, verify that calling deleteWorkout with that ID removes all instances, as `filter` would remove all matching items.
        // TODO implement test
    }

    @Test
    fun `Thread safety and concurrent modifications`() {
        // Test for race conditions by calling methods like addWorkout, updateWorkout, and deleteWorkout from multiple threads simultaneously to check for ConcurrentModificationException or inconsistent final state.
        // TODO implement test
    }

}
