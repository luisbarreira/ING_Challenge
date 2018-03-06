import java.util.Arrays;

/**
 * This is a test class for In-flight entertainment system
 * 
 * Imagine that you are working for an airline company. Lately they are
 * receiving complaints from their customers regarding the in-flight
 * entertainment system. The issue is that for the longer flights users would
 * like to start a second movie when their first one ends, but the plane usually
 * lands before they can see the ending.
 * <p>
 * This class will choose N movies whose total time is equal with the exact
 * flight length. It has 3 input parameters:
 * <ul>
 * <li>An array of integers that represents the time in minutes for each movie</li>
 * <li>The flight length in minutes</li>
 * <li>The number of movies to 'fit' into the flight duration</li>
 * </ul>
 * The output is a boolean that will indicate if there are N movies whose sum
 * equals the flight length. The user is watching exactly N movies The user
 * should not watch the same movie twice
 * <p>
 * 
 * @author Luis Barreira
 * @version 1.0
 */
public class InFlightMovies {

	/*
	 * Default number of movies to fit into flight duration
	 */
	private static final int DEFAULT_N_MOVIES = 2;

	/**
	 * Returns true if there is a subset of moviesDuration[] with sum equal to
	 * flightDuration, and the subset is of length equal to moviesCount.
	 * 
	 * This method is recursive and will start by analyzing the end of
	 * moviesDuration array and subtracting it from the flightDuration, at same
	 * time decreasing moviesCount by 1 for each value found. It will proceed
	 * until flightDuration and moviesCount are both 0.
	 * <p>
	 *
	 * @param moviesDuration
	 *            the array for flight movies duration in minutes
	 * @param flightDuration
	 *            the total duration for the flight in minutes
	 * @param moviesCount
	 *            the number of movies to fit into the flight duration
	 * @param lastIdx
	 *            the last index of the moviesDuration array to take into
	 *            consideration
	 * @return true if a subset of movies was found that match both movies count
	 *         and flight duration
	 */
	private static boolean moviesFitIntoDuration(int moviesDuration[],
			int flightDuration, int moviesCount, int lastIdx) {

		/*
		 * When flightDuration reach 0 a subset was found, and if moviesCount
		 * was also 0 the match respects the number of movies
		 */
		if (flightDuration == 0 && moviesCount == 0)
			return true;
		/*
		 * If either flightDuration==0 or moviesCount is equal or below 0, and
		 * they aren't both 0, then the match found is not the desired one
		 */
		else if (flightDuration == 0 || moviesCount <= 0)
			return false;

		/*
		 * If we reach the first index and flightDuration still not 0, then this
		 * iteration was not valid
		 */
		if (lastIdx == 0 && flightDuration != 0)
			return false;

		/*
		 * If last movie duration is greater flight duration, then ignore it
		 */
		if (moviesDuration[lastIdx - 1] > flightDuration)
			return moviesFitIntoDuration(moviesDuration, flightDuration, moviesCount, lastIdx - 1);

		/*
		 * Proceed to check if intended subset can be achieved by either:
		 * Excluding the last element of the array Including the last element,
		 * and thus decrementing the flightDuration by the movie time, and the
		 * movieCount by 1
		 */
		return 
			moviesFitIntoDuration(moviesDuration, flightDuration, moviesCount, lastIdx - 1)
			|| 
			moviesFitIntoDuration(moviesDuration, flightDuration - moviesDuration[lastIdx - 1], moviesCount - 1, lastIdx - 1);
	}

	/**
	 * Main method to find N number of movies that fit exactly into a flight
	 * duration
	 *
	 * @param args
	 *            the command line argument: [0] the total duration for the
	 *            flight in minutes [1] the number of movies to fit into the
	 *            flight duration [2] the array for flight movies duration in
	 *            minutes
	 */
	public static void main(String[] args) {

		int flightDuration = 0;
		int moviesCount = DEFAULT_N_MOVIES;
		int moviesDuration[] = null;

		/*
		 * Default parameters
		 */
		if (args.length == 0) {

			moviesDuration = new int[] { 30, 77, 120, 95, 45, 18, 25, 75, 118 };
			flightDuration = 195;
		}
		/*
		 * Invalid command line arguments
		 */
		else if (args.length < 3) {
			System.out.println("USAGE:");
			System.out.println("java InFlightMovies <flight_duration_minutes> <number_of_movies_to_watch> <comma_separated_movie_list_in_minutes>");
			return;
		}
		/*
		 * Parsing command line arguments
		 */
		else {
			flightDuration = Integer.parseInt(args[0]);
			moviesCount = Integer.parseInt(args[1]);
			// convert comma separated array into int[]
			moviesDuration = Arrays.stream(args[2].split(",")).mapToInt(Integer::parseInt).toArray();
		}

		if (moviesFitIntoDuration(moviesDuration, flightDuration, moviesCount,
				moviesDuration.length) == true)
			System.out.println("Found " + moviesCount + " movies that fit into flight duration");
		else
			System.out.println("There aren't " + moviesCount + " movies that fit into flight duration");

	}

}
