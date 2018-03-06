import java.util.Arrays;

/**
 * This is a test class for Merge two different phone agendas
 * 
 * Imagine that you need to organize a party and to create a guest list. You're
 * adding alphabetically all your friends from your phone agenda in a huge array
 * you create in memory (you have a lot of friends). You start your list with
 * your best friend who is somewhere in the middle of your agenda. When you've
 * reached the end of the agenda, you start from the beginning and do the same
 * thing until you reach your best friend.
 * <p>
 * Now you have an array of names that are mostly alphabetical, except they
 * start somewhere in the middle of the alphabet, reach the end, and start from
 * the beginning of the alphabet.
 * <p>
 * The objective is finding the index of the "changing point," which is where
 * you start adding guests from the beginning of your agenda.
 * 
 * @author Luis Barreira
 * @version 1.0
 */
public class AgendaChangingPoint {

	/*
	 * Difference between Uppercase ASCII code and Lowercase
	 */
	private static final int UPPER_LOWER_ASCII_DIFF = 32;
	/*
	 * ASCII code for first Uppercase character 'A'
	 */
	private static final int UPPER_ASCII_INIT = 97;
	/*
	 * Either compares names by only the first character (false) or by the
	 * entire word (true)
	 */
	private static boolean IN_DEPTH_ANALYSIS = false;

	/**
	 * Returns true if the 2th string is alphabetical lower then the 1th string.
	 * 
	 * This method will either (depending on IN_DEPTH_ANALYSIS flag) just
	 * compares the first character of the string, or recursively compares the
	 * complete string character by character
	 *
	 * @param str1
	 *            the first string to compare
	 * @param str2
	 *            the second string to compare
	 * @param idx
	 *            the current string character index to compare
	 * @return true if the second string is lower then the first
	 */
	private static boolean charAtIdxisLower(String str1, String str2, int idx) {

		int char1 = str1.codePointAt(idx);
		int char2 = str2.codePointAt(idx);

		/*
		 * Converts lowercase characters to Uppercase for easier comparison
		 */
		char1 = char1 >= UPPER_ASCII_INIT ? char1 - UPPER_LOWER_ASCII_DIFF : char1;
		char2 = char2 >= UPPER_ASCII_INIT ? char2 - UPPER_LOWER_ASCII_DIFF : char2;

		if (char2 < char1) // 2th String is lower then 1th
			return true;
		else if (char2 > char1) // 2th String is bigger then 1th
			return false;
		else if (str1.length() == idx + 1) // 1th String is smaller or equal then then 2th
			return false;
		else if (str2.length() == idx + 1) // 1th String is bigger then then 2th
			return true;
		else if (!IN_DEPTH_ANALYSIS) // up to this char the Strings are equal and no need to test next char
			return false;
		else
			// up to this char the Strings are equal, test next char
			return charAtIdxisLower(str1, str2, idx + 1);
	}

	/**
	 * Returns the array index at which a changing point in alphabetic sorting
	 * happens.
	 * 
	 * @param strList
	 *            the string array containing both agendas names, each agenda
	 *            sorted alphabetically
	 * @return the array index at which the second agenda is detected, the
	 *         changing point
	 */
	private static int getChangingPoint(String[] strList) {

		for (int i = 0; i < strList.length - 1; i++)
			if (charAtIdxisLower(strList[i], strList[i + 1], 0))
				return i + 1;

		// finishing testing the array, so breaking point at first position
		return 0;
	}

	/**
	 * Main method to find the changing point in 2 agendas
	 *
	 * @param args
	 *            the command line argument. [0] (optional) -d for activating In
	 *            Depth Analysis [1..n] Each position is a different name of the
	 *            agenda.
	 */
	public static void main(String[] args) {

		String[] agenda = null;

		/*
		 * Default parameters
		 */
		if (args.length == 0) {
			agenda = new String[] { 
					"George", 
					"Gregory", 
					"Hugo", 
					"James",
					"Julia", 
					"Lara", 
					"Noah", 
					"Pamela", 
					"Paul", 
					"Pauline",
					"Sandra", 
					"Salima", // <-- changing point! If analyzing the the whole string
					"Tudor", 
					"Adam", // <-- changing point! If only analyzing the first Char
					"Alesia", 
					"Bridget", 
					"Charlie", 
					"Celine", 
					"Diane", 
					"Fabio" };

		}
		/*
		 * Invalid command line arguments
		 */
		else if (args.length < 2) {
			System.out.println("USAGE:");
			System.out.println("java AgendaChangingPoint -d(optional) <Agenda_Name_1> <Agenda_Name_2> <Agenda_Name_3> ...");
			return;
		}
		/*
		 * Parsing command line arguments
		 */
		else if (args[0].equalsIgnoreCase("-d")) {

				IN_DEPTH_ANALYSIS = true;
				agenda = Arrays.stream(args).skip(1).toArray(String[]::new);
		}
		else
			agenda = args;

		int ret = getChangingPoint(agenda);
		System.out.print("Changing point found at index: " + ret);

	}

}
