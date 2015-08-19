package yoshikihigo.fbparser;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FBMeter {

	public static void main(final String[] args) {

		FBParserConfig.initialize(args);

		final List<String> xmls = FBParserConfig.getInstance().getFBRESULTS();
		final Map<String, List<BugInstance>> bugInstances = new HashMap<String, List<BugInstance>>();
		for (String xml : xmls) {
			final FBParser parser = new FBParser(xml);
			parser.perform();
			final List<BugInstance> bugs = parser.getBugInstances();
			bugInstances.put(xml, bugs);
		}

		final List<Transition> transitions = new ArrayList<Transition>();
		for (int i = 2; i < xmls.size(); i++) {
			final String earlierXMLFile = xmls.get(i - 1);
			final String latterXMLFile = xmls.get(i);

			final SortedSet<BugInstance> earlierBugs = new TreeSet<>(
					new BugInstance.RankLocationTypeComparator());
			earlierBugs.addAll(bugInstances.get(earlierXMLFile));
			final SortedSet<BugInstance> latterBugs = new TreeSet<>(
					new BugInstance.RankLocationTypeComparator());
			latterBugs.addAll(bugInstances.get(latterXMLFile));

			final SortedSet<BugInstance> survivingBugs = new TreeSet<>(
					new BugInstance.RankLocationTypeComparator());
			final SortedSet<BugInstance> removedBugs = new TreeSet<BugInstance>(
					new BugInstance.RankLocationTypeComparator());
			for (final BugInstance bug : earlierBugs) {

				if (latterBugs.contains(bug)) {
					survivingBugs.add(bug);
				} else {
					removedBugs.add(bug);
				}
			}

			final SortedSet<BugInstance> addedBugs = new TreeSet<>(
					new BugInstance.RankLocationTypeComparator());
			for (final BugInstance bug : latterBugs) {
				if (!earlierBugs.contains(bug)) {
					addedBugs.add(bug);
				}
			}

			final Transition pair = new Transition(earlierXMLFile,
					latterXMLFile, survivingBugs, addedBugs, removedBugs);
			transitions.add(pair);
		}

		if (FBParserConfig.getInstance().hasMETRICSRESULTCSV()) {
			final String csvFile = FBParserConfig.getInstance()
					.getMETRICSRESULTCSV();
			printInCSV(csvFile, transitions);
		}

		if (FBParserConfig.getInstance().hasMETRICSRESULTXLSX()) {
			final String xlsxFile = FBParserConfig.getInstance()
					.getMETRICSRESULTXLSX();
			printInXLSX(xlsxFile, transitions);
		}
	}

	static private int countBugInstances(
			final SortedSet<BugInstance> instances, final String type) {
		int count = 0;
		for (final BugInstance instance : instances) {
			if (instance.pattrn.type.equals(type)) {
				count++;
			}
		}
		return count;
	}

	static private void printInCSV(final String path,
			final List<Transition> transitions) {

		try (final PrintWriter writer = new PrintWriter(new OutputStreamWriter(
				new FileOutputStream(path), "UTF-8"))) {

			final StringBuilder typeText = new StringBuilder();
			final StringBuilder rankText = new StringBuilder();
			final StringBuilder priorityText = new StringBuilder();
			final StringBuilder categoryText = new StringBuilder();
			typeText.append("TYPE, ");
			rankText.append("RANK, ");
			priorityText.append("PRIORITY, ");
			categoryText.append("CATEGORY, ");
			for (final BugPattern pattern : BugPattern.getBugPatterns()) {
				typeText.append(pattern.type + "[number-of-surviving-bugs],");
				typeText.append(pattern.type + "[number-of-removed-bugs],");
				typeText.append(pattern.type + "[number-of-added-bugs],");
				typeText.append(pattern.type + "[ratio-of-surviving], ");
				typeText.append(pattern.type + "[ratio-of-solved-old], ");
				typeText.append(pattern.type + "[ratio-of-solved-new], ");
				rankText.append(Integer.toString(pattern.rank) + ", ");
				rankText.append(Integer.toString(pattern.rank) + ", ");
				rankText.append(Integer.toString(pattern.rank) + ", ");
				rankText.append(Integer.toString(pattern.rank) + ", ");
				rankText.append(Integer.toString(pattern.rank) + ", ");
				rankText.append(Integer.toString(pattern.rank) + ", ");
				priorityText.append(Integer.toString(pattern.priority) + ", ");
				priorityText.append(Integer.toString(pattern.priority) + ", ");
				priorityText.append(Integer.toString(pattern.priority) + ", ");
				priorityText.append(Integer.toString(pattern.priority) + ", ");
				priorityText.append(Integer.toString(pattern.priority) + ", ");
				priorityText.append(Integer.toString(pattern.priority) + ", ");
				categoryText.append(pattern.category + ", ");
				categoryText.append(pattern.category + ", ");
				categoryText.append(pattern.category + ", ");
				categoryText.append(pattern.category + ", ");
				categoryText.append(pattern.category + ", ");
				categoryText.append(pattern.category + ", ");
			}
			typeText.deleteCharAt(typeText.length() - 1);
			rankText.deleteCharAt(rankText.length() - 1);
			priorityText.deleteCharAt(priorityText.length() - 1);
			categoryText.deleteCharAt(categoryText.length() - 1);
			writer.println(typeText);
			writer.println(rankText);
			writer.println(priorityText);
			writer.println(categoryText);

			for (final Transition pair : transitions) {

				final StringBuilder dataText = new StringBuilder();
				dataText.append(StringUtility.getName(pair.earlierXMLFile));
				dataText.append("--");
				dataText.append(StringUtility.getName(pair.latterXMLFile));
				dataText.append(", ");

				for (final BugPattern pattern : BugPattern.getBugPatterns()) {

					final int numberOfSurvivingBugs = countBugInstances(
							pair.survivingBugs, pattern.type);
					final int numberOfAddedBugs = countBugInstances(
							pair.addedBugs, pattern.type);
					final int numberOfRemovedBugs = countBugInstances(
							pair.removedBugs, pattern.type);

					float ratioOfSurviving = (float) numberOfSurvivingBugs
							/ (float) (numberOfSurvivingBugs + numberOfRemovedBugs);
					float ratioOfSolvedOld = (float) numberOfRemovedBugs
							/ (float) (numberOfSurvivingBugs + numberOfRemovedBugs);
					float ratioOfSolvednew = (float) numberOfRemovedBugs
							/ (float) (numberOfSurvivingBugs + numberOfAddedBugs);

					dataText.append(Integer.toString(numberOfSurvivingBugs));
					dataText.append(", ");
					dataText.append(Integer.toString(numberOfRemovedBugs));
					dataText.append(", ");
					dataText.append(Integer.toString(numberOfAddedBugs));
					dataText.append(", ");
					dataText.append(Float.toString(ratioOfSurviving));
					dataText.append(", ");
					dataText.append(Float.toString(ratioOfSolvedOld));
					dataText.append(", ");
					dataText.append(Float.toString(ratioOfSolvednew));
					dataText.append(", ");
				}
				writer.println(dataText.toString());
			}
		}

		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	static private void printInXLSX(final String path,
			final List<Transition> transitions) {

		try (final Workbook book = new XSSFWorkbook();
				final OutputStream stream = new FileOutputStream(path)) {

			final Sheet sheet = book.createSheet();
			book.setSheetName(0, "metrics");

			final Row typeRow = sheet.createRow(0);
			final Row rankRow = sheet.createRow(1);
			final Row priorityRow = sheet.createRow(2);
			final Row categoryRow = sheet.createRow(3);
			final Row metricRow = sheet.createRow(4);

			typeRow.createCell(0).setCellValue("TYPE");
			rankRow.createCell(0).setCellValue("RANK");
			priorityRow.createCell(0).setCellValue("PRIORITY");
			categoryRow.createCell(0).setCellValue("CATETORY");
			metricRow.createCell(0).setCellValue("METRIC");

			int titleColumn = 1;

			for (final BugPattern pattern : BugPattern.getBugPatterns()) {

				typeRow.createCell(titleColumn).setCellValue(pattern.type);
				sheet.addMergedRegion(new CellRangeAddress(0, 0, titleColumn,
						titleColumn + 5));
				rankRow.createCell(titleColumn).setCellValue(pattern.rank);
				sheet.addMergedRegion(new CellRangeAddress(1, 1, titleColumn,
						titleColumn + 5));
				priorityRow.createCell(titleColumn).setCellValue(
						pattern.priority);
				sheet.addMergedRegion(new CellRangeAddress(2, 2, titleColumn,
						titleColumn + 5));
				categoryRow.createCell(titleColumn).setCellValue(
						pattern.category);
				sheet.addMergedRegion(new CellRangeAddress(3, 3, titleColumn,
						titleColumn + 5));
				metricRow.createCell(titleColumn).setCellValue(
						"number-of-surviving-bugs");
				metricRow.createCell(titleColumn + 1).setCellValue(
						"number-of-removed-bugs");
				metricRow.createCell(titleColumn + 2).setCellValue(
						"number-of-added-bugs");
				metricRow.createCell(titleColumn + 3).setCellValue(
						"ratio-of-surviving-bugs");
				metricRow.createCell(titleColumn + 4).setCellValue(
						"number-of-solved-old");
				metricRow.createCell(titleColumn + 5).setCellValue(
						"number-of-solved-new");

				titleColumn += 6;
			}

			int dataRow = 5;
			for (final Transition pair : transitions) {

				final StringBuilder dataText = new StringBuilder();
				dataText.append(StringUtility.getName(pair.earlierXMLFile));
				dataText.append("--");
				dataText.append(StringUtility.getName(pair.latterXMLFile));
				dataText.append(", ");

				final Row row = sheet.createRow(dataRow);
				row.createCell(0).setCellValue(dataText.toString());

				int dataColumn = 1;
				for (final BugPattern pattern : BugPattern.getBugPatterns()) {

					final int numberOfSurvivingBugs = countBugInstances(
							pair.survivingBugs, pattern.type);
					final int numberOfAddedBugs = countBugInstances(
							pair.addedBugs, pattern.type);
					final int numberOfRemovedBugs = countBugInstances(
							pair.removedBugs, pattern.type);

					float ratioOfSurviving = (float) numberOfSurvivingBugs
							/ (float) (numberOfSurvivingBugs + numberOfRemovedBugs);
					float ratioOfSolvedOld = (float) numberOfRemovedBugs
							/ (float) (numberOfSurvivingBugs + numberOfRemovedBugs);
					float ratioOfSolvednew = (float) numberOfRemovedBugs
							/ (float) (numberOfSurvivingBugs + numberOfAddedBugs);

					row.createCell(dataColumn).setCellValue(
							numberOfSurvivingBugs);
					row.createCell(dataColumn + 1).setCellValue(
							numberOfRemovedBugs);
					row.createCell(dataColumn + 2).setCellValue(
							numberOfAddedBugs);
					row.createCell(dataColumn + 3).setCellValue(
							ratioOfSurviving);
					row.createCell(dataColumn + 4).setCellValue(
							ratioOfSolvedOld);
					row.createCell(dataColumn + 5).setCellValue(
							ratioOfSolvednew);

					dataColumn += 6;
				}

				dataRow += 1;
			}

			book.write(stream);
		}

		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	static class Transition {
		final String earlierXMLFile;
		final String latterXMLFile;
		final SortedSet<BugInstance> survivingBugs;
		final SortedSet<BugInstance> addedBugs;
		final SortedSet<BugInstance> removedBugs;

		Transition(final String earlierXMLFile, final String latterXMLFile,
				final SortedSet<BugInstance> survivingBugs,
				final SortedSet<BugInstance> addedBugs,
				final SortedSet<BugInstance> removedBugs) {
			this.earlierXMLFile = earlierXMLFile;
			this.latterXMLFile = latterXMLFile;
			this.survivingBugs = survivingBugs;
			this.addedBugs = addedBugs;
			this.removedBugs = removedBugs;
		}
	}
}
