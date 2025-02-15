package nh3.ammonia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class BugPattern implements Comparable<BugPattern> {

  final static public String[] NAMES = new String[] {"SQL_BAD_RESULTSET_ACCESS",
      "SQL_BAD_PREPARED_STATEMENT_ACCESS", "RE_BAD_SYNTAX_FOR_REGULAR_EXPRESSION",
      "RE_POSSIBLE_UNINTENDED_PATTERN", "RE_CANT_USE_FILE_SEPARATOR_AS_REGULAR_EXPRESSION",
      "RV_CHECK_FOR_POSITIVE_INDEXOF", "RV_DONT_JUST_NULL_CHECK_READLINE", "NP_BOOLEAN_RETURN_NULL",
      "CN_IDIOM", "CN_IDIOM_NO_SUPER_CALL", "CN_IMPLEMENTS_CLONE_BUT_NOT_CLONEABLE",
      "CI_CONFUSED_INHERITANCE", "IA_AMBIGUOUS_INVOCATION_OF_INHERITED_OR_OUTER_METHOD",
      "HRS_REQUEST_PARAMETER_TO_HTTP_HEADER", "HRS_REQUEST_PARAMETER_TO_COOKIE",
      "XSS_REQUEST_PARAMETER_TO_JSP_WRITER", "XSS_REQUEST_PARAMETER_TO_SERVLET_WRITER",
      "XSS_REQUEST_PARAMETER_TO_SEND_ERROR", "IMSE_DONT_CATCH_IMSE", "DE_MIGHT_DROP",
      "DE_MIGHT_IGNORE", "DMI_EMPTY_DB_PASSWORD", "DMI_CONSTANT_DB_PASSWORD",
      "DMI_USELESS_SUBSTRING", "DMI_HARDCODED_ABSOLUTE_FILENAME",
      "NP_IMMEDIATE_DEREFERENCE_OF_READLINE", "RV_01_TO_INT", "DM_DEFAULT_ENCODING",
      "DM_RUN_FINALIZERS_ON_EXIT", "DM_STRING_CTOR", "DM_STRING_VOID_CTOR", "DM_STRING_TOSTRING",
      "DM_GC", "DM_BOOLEAN_CTOR", "DM_EXIT", "DM_BOXED_PRIMITIVE_TOSTRING", "DM_NEW_FOR_GETCLASS",
      "DM_NEXTINT_VIA_NEXTDOUBLE", "DM_USELESS_THREAD", "DM_MONITOR_WAIT_ON_CONDITION",
      "DMI_CALLING_NEXT_FROM_HASNEXT", "BIT_IOR_OF_SIGNED_BYTE",
      "INT_BAD_COMPARISON_WITH_NONNEGATIVE_VALUE", "INT_BAD_COMPARISON_WITH_SIGNED_BYTE",
      "INT_BAD_REM_BY_1", "DMI_ANNOTATION_IS_NOT_VISIBLE_TO_REFLECTION", "INT_VACUOUS_COMPARISON",
      "BC_EQUALS_METHOD_SHOULD_WORK_FOR_ALL_OBJECTS", "DMI_RANDOM_USED_ONLY_ONCE",
      "DMI_LONG_BITS_TO_DOUBLE_INVOKED_ON_INT", "DMI_THREAD_PASSED_WHERE_RUNNABLE_EXPECTED",
      "DB_DUPLICATE_BRANCHES", "DB_DUPLICATE_SWITCH_CLAUSES", "AM_CREATES_EMPTY_ZIP_FILE_ENTRY",
      "AM_CREATES_EMPTY_JAR_FILE_ENTRY", "FI_FINALIZER_NULLS_FIELDS",
      "FI_FINALIZER_ONLY_NULLS_FIELDS", "BC_BAD_CAST_TO_CONCRETE_COLLECTION",
      "BC_BAD_CAST_TO_ABSTRACT_COLLECTION", "BC_UNCONFIRMED_CAST", "BC_IMPOSSIBLE_CAST",
      "BC_IMPOSSIBLE_INSTANCEOF", "BC_VACUOUS_INSTANCEOF", "NP_NULL_INSTANCEOFY",
      "QF_QUESTIONABLE_FOR_LOOP", "DLS_DEAD_LOCAL_STORE", "IP_PARAMETER_IS_DEAD_BUT_OVERWRITTEN",
      "DLS_DEAD_LOCAL_STORE_OF_NULL", "DLS_DEAD_STORE_OF_CLASS_LITERAL", "DC_DOUBLECHECK",
      "ESync_EMPTY_SYNC", "FI_PUBLIC_SHOULD_BE_PROTECTED", "FI_EMPTY", "FI_NULLIFY_SUPER",
      "FI_USELESS", "FI_MISSING_SUPER_CALL", "FI_EXPLICIT_INVOCATION", "FE_FLOATING_POINT_EQUALITY",
      "FE_TEST_IF_EQUAL_TO_NOT_A_NUMBER", "EQ_DONT_DEFINE_EQUALS_FOR_ENUM", "EQ_SELF_USE_OBJECT",
      "EQ_SELF_NO_OBJECT", "CO_SELF_NO_OBJECT", "HE_HASHCODE_USE_OBJECT_EQUALS",
      "HE_HASHCODE_NO_EQUALS", "HE_EQUALS_USE_HASHCODE", "HE_EQUALS_NO_HASHCODE",
      "HE_INHERITS_EQUALS_USE_HASHCODE", "CO_ABSTRACT_SELF", "EQ_ABSTRACT_SELF",
      "HE_USE_OF_UNHASHABLE_CLASS", "EQ_COMPARETO_USE_OBJECT_EQUALS", "EQ_DOESNT_OVERRIDE_EQUALS",
      "EQ_OTHER_USE_OBJECT", "EQ_OTHER_NO_OBJECT", "IS2_INCONSISTENT_SYNC", "IS_FIELD_NOT_GUARDED",
      "MSF_MUTABLE_SERVLET_FIELD", "JLM_JSR166_LOCK_MONITORENTER", "MF_METHOD_MASKS_FIELD",
      "MF_CLASS_MASKS_FIELD", "MWN_MISMATCHED_WAIT", "MWN_MISMATCHED_NOTIFY", "NN_NAKED_NOTIFY",
      "J2EE_STORE_OF_NON_SERIALIZABLE_OBJECT_INTO_SESSION", "NS_NON_SHORT_CIRCUIT",
      "NS_DANGEROUS_NON_SHORT_CIRCUIT", "NP_NULL_ON_SOME_PATH_MIGHT_BE_INFEASIBLE",
      "NP_TOSTRING_COULD_RETURN_NULL", "NP_CLONE_COULD_RETURN_NULL", "NP_ALWAYS_NULL_EXCEPTION",
      "NP_ALWAYS_NULL", "NP_STORE_INTO_NONNULL_FIELD", "NP_NULL_ON_SOME_PATH_EXCEPTION",
      "NP_NULL_ON_SOME_PATH", "NP_NULL_PARAM_DEREF_NONVIRTUAL",
      "NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS", "NP_NULL_PARAM_DEREF",
      "RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE", "RCN_REDUNDANT_NULLCHECK_OF_NULL_VALUE",
      "RCN_REDUNDANT_NULLCHECK_OF_NONNULL_VALUE", "RCN_REDUNDANT_COMPARISON_TWO_NULL_VALUES",
      "RCN_REDUNDANT_COMPARISON_OF_NULL_AND_NONNULL_VALUE",
      "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE", "NP_GUARANTEED_DEREF",
      "NP_GUARANTEED_DEREF_ON_EXCEPTION_PATH", "NP_EQUALS_SHOULD_HANDLE_NULL_ARGUMENT",
      "NP_ARGUMENT_MIGHT_BE_NULL", "OS_OPEN_STREAM", "OS_OPEN_STREAM_EXCEPTION_PATH",
      "ODR_OPEN_DATABASE_RESOURCE", "ODR_OPEN_DATABASE_RESOURCE_EXCEPTION_PATH",
      "DLS_DEAD_LOCAL_STORE_IN_RETURN", "EC_BAD_ARRAY_COMPARE", "DLS_OVERWRITTEN_INCREMENT",
      "ICAST_BAD_SHIFT_AMOUNT", "ICAST_QUESTIONABLE_UNSIGNED_RIGHT_SHIFT", "DMI_BAD_MONTH",
      "IM_MULTIPLYING_RESULT_OF_IREM", "IM_BAD_CHECK_FOR_ODD", "DMI_INVOKING_TOSTRING_ON_ARRAY",
      "DMI_INVOKING_TOSTRING_ON_ANONYMOUS_ARRAY", "IM_AVERAGE_COMPUTATION_COULD_OVERFLOW",
      "IC_SUPERCLASS_USES_SUBCLASS_DURING_INITIALIZATION", "ICAST_INTEGER_MULTIPLY_CAST_TO_LONG",
      "BX_UNBOXED_AND_COERCED_FOR_TERNARY_OPERATOR", "BX_BOXING_IMMEDIATELY_UNBOXED",
      "BX_BOXING_IMMEDIATELY_UNBOXED_TO_PERFORM_COERCION", "VA_FORMAT_STRING_ARG_MISMATCH",
      "ES_COMPARING_STRINGS_WITH_EQ", "ES_COMPARING_PARAMETER_STRING_WITH_EQ", "RC_REF_COMPARISON",
      "EC_UNRELATED_TYPES", "EC_NULL_ARG", "EC_UNRELATED_CLASS_AND_INTERFACE",
      "EC_UNRELATED_INTERFACES", "EC_ARRAY_AND_NONARRAY", "EC_BAD_ARRAY_COMPARE",
      "EC_UNRELATED_TYPES_USING_POINTER_EQUALITY", "MS_EXPOSE_REP", "MS_SHOULD_BE_FINAL",
      "RU_INVOKE_RUN", "SA_FIELD_SELF_COMPARISON", "SA_LOCAL_SELF_COMPARISON",
      "SA_FIELD_SELF_COMPUTATION", "SA_LOCAL_SELF_COMPUTATION", "SA_FIELD_DOUBLE_ASSIGNMENT",
      "SA_FIELD_SELF_COMPARISON", "SA_LOCAL_SELF_COMPARISON", "SA_FIELD_SELF_COMPUTATION",
      "SA_LOCAL_SELF_COMPUTATION", "SWL_SLEEP_WITH_LOCK_HELD", "SP_SPIN_ON_FIELD",
      "SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE",
      "SQL_PREPARED_STATEMENT_GENERATED_FROM_NONCONSTANT_STRING", "TLW_TWO_LOCK_WAIT",
      "UW_UNCOND_WAIT", "UR_UNINIT_READ", "GC_UNRELATED_TYPES", "UL_UNRELEASED_LOCK",
      "UL_UNRELEASED_LOCK_EXCEPTION_PATH", "UG_SYNC_SET_UNSYNC_GET", "UCF_USELESS_CONTROL_FLOW",
      "UCF_USELESS_CONTROL_FLOW_NEXT_LINE", "ICAST_IDIV_CAST_TO_DOUBLE",
      "ICAST_INT_CAST_TO_DOUBLE_PASSED_TO_CEIL", "ICAST_INT_CAST_TO_FLOAT_PASSED_TO_ROUND",
      "BIT_AND", "BIT_AND_ZZ", "BIT_IOR", "BIT_SIGNED_CHECK", "BIT_SIGNED_CHECK_HIGH_BIT",
      "ITA_INEFFICIENT_TO_ARRAY", "IL_INFINITE_LOOP", "IL_INFINITE_RECURSIVE_LOOP",
      "IL_CONTAINER_ADDED_TO_ITSELF", "IL_INFINITE_RECURSIVE_LOOP", "IL_CONTAINER_ADDED_TO_ITSELF",
      "UI_INHERITANCE_UNSAFE_GETRESOURCE", "SI_INSTANCE_BEFORE_FINALS_ASSIGNED",
      "IC_INIT_CIRCULARITY", "ISC_INSTANTIATE_STATIC_CLASS", "LI_LAZY_INIT_STATIC",
      "LI_LAZY_INIT_UPDATE_STATIC", "NP_LOAD_OF_KNOWN_NULL_VALUE", "RV_RETURN_VALUE_IGNORED",
      "RV_RETURN_VALUE_IGNORED_BAD_PRACTICE", "RV_EXCEPTION_NOT_THROWN",
      "MTIA_SUSPECT_STRUTS_INSTANCE_FIELD", "MTIA_SUSPECT_SERVLET_INSTANCE_FIELD",
      "ML_SYNC_ON_UPDATED_FIELD", "NM_WRONG_PACKAGE", "NM_WRONG_PACKAGE_INTENTIONAL",
      "NM_VERY_CONFUSING", "NM_VERY_CONFUSING_INTENTIONAL", "NM_CONFUSING",
      "NM_METHOD_CONSTRUCTOR_CONFUSION", "NM_LCASE_HASHCODE", "NM_LCASE_TOSTRING", "NM_BAD_EQUAL",
      "NM_CLASS_NAMING_CONVENTION", "NM_FIELD_NAMING_CONVENTION", "NM_METHOD_NAMING_CONVENTION",
      "NM_CLASS_NOT_EXCEPTION", "NM_SAME_SIMPLE_NAME_AS_SUPERCLASS",
      "NM_SAME_SIMPLE_NAME_AS_INTERFACE", "NM_FUTURE_KEYWORD_USED_AS_IDENTIFIER", "DM_NUMBER_CTOR",
      "DM_FP_NUMBER_CTOR", "EQ_OVERRIDING_EQUALS_NOT_SYMMETRIC", "EQ_ALWAYS_TRUE",
      "EQ_ALWAYS_FALSE", "EQ_COMPARING_CLASS_NAMES", "EQ_UNUSUAL", "EQ_GETCLASS_AND_CLASS_CONSTANT",
      "PZLA_PREFER_ZERO_LENGTH_ARRAYS", "PS_PUBLIC_SEMAPHORES",
      "QBA_QUESTIONABLE_BOOLEAN_ASSIGNMENT", "RR_NOT_CHECKED", "SR_NOT_CHECKED",
      "REC_CATCH_EXCEPTION", "SC_START_IN_CTOR", "STCAL_STATIC_CALENDAR_INSTANCE",
      "STCAL_STATIC_SIMPLE_DATE_FORMAT_INSTANCE", "STCAL_INVOKE_ON_STATIC_CALENDAR_INSTANCE",
      "STCAL_INVOKE_ON_STATIC_DATE_FORMAT_INSTANCE", "SBSC_USE_STRINGBUFFER_CONCATENATION",
      "SIO_SUPERFLUOUS_INSTANCEOF", "STI_INTERRUPTED_ON_CURRENTTHREAD",
      "NP_SYNC_AND_NULL_CHECK_FIELD", "WL_USING_GETCLASS_RATHER_THAN_CLASS_LITERAL",
      "ML_SYNC_ON_FIELD_TO_GUARD_CHANGING_THAT_FIELD", "DMI_BLOCKING_METHODS_ON_URL",
      "UM_UNNECESSARY_MATH", "NP_UNWRITTEN_FIELD", "UWF_NULL_FIELD", "SS_SHOULD_BE_STATIC",
      "SIC_INNER_SHOULD_BE_STATIC", "SIC_INNER_SHOULD_BE_STATIC_ANON",
      "SIC_INNER_SHOULD_BE_STATIC_NEEDS_THIS", "USM_USELESS_SUBCLASS_METHOD",
      "USM_USELESS_ABSTRACT_METHOD", "VA_PRIMITIVE_ARRAY_PASSED_TO_OBJECT_VARARG",
      "VO_VOLATILE_REFERENCE_TO_ARRAY", "WA_NOT_IN_LOOP", "WA_AWAIT_NOT_IN_LOOP",
      "NO_NOTIFY_NOT_NOTIFYALL", "WMI_WRONG_MAP_ITERATOR", "XFB_XML_FACTORY_BYPASS",
      "EI_EXPOSE_REP", "OBL_UNSATISFIED_OBLIGATION", "OBL_UNSATISFIED_OBLIGATION_EXCEPTION_EDGE",
      "SE_BAD_FIELD", "UC_USELESS_CONDITION", "URF_UNREAD_FIELD",
      "URF_UNREAD_PUBLIC_OR_PROTECTED_FIELD", "UUF_UNUSED_FIELD",
      "DP_CREATE_CLASSLOADER_INSIDE_DO_PRIVILEGED", "EI_EXPOSE_REP2", "MS_PKGPROTECT",
      "SA_FIELD_SELF_ASSIGNMENT", "SF_SWITCH_NO_DEFAULT", "UUF_UNUSED_PUBLIC_OR_PROTECTED_FIELD",
      "DMI_INVOKING_HASHCODE_ON_ARRAY", "INT_VACUOUS_BIT_OPERATION",
      "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD", "EI_EXPOSE_STATIC_REP2", "SE_BAD_FIELD_STORE",
      "SE_COMPARATOR_SHOULD_BE_SERIALIZABLE", "RC_REF_COMPARISON_BAD_PRACTICE_BOOLEAN",
      "SE_NO_SERIALVERSIONID", "RV_NEGATING_RESULT_OF_COMPARETO", "DMI_COLLECTION_OF_URLS",
      "INT_BAD_COMPARISON_WITH_INT_VALUE", "RpC_REPEATED_CONDITIONAL_TEST",
      "DM_BOXED_PRIMITIVE_FOR_PARSING", "MS_OOI_PKGPROTECT"};

  final static private Map<String, BugPattern> PATTERNS = new HashMap<>();

  // static {
  // for (final String name : NAMES) {
  // PATTERNS.put(name, new BugPattern(name));
  // }
  // }

  public static BugPattern getBugPattern(final String name) {
    return PATTERNS.get(name);
  }

  static public SortedSet<BugPattern> getBugPatterns() {
    final SortedSet<BugPattern> patterns = new TreeSet<>();
    patterns.addAll(PATTERNS.values());
    return patterns;
  }

  static public void addBugPattern(final BugPattern pattern) {
    PATTERNS.put(pattern.type, pattern);
  }

  final public String type;
  final public String category;
  final private Map<Object, List<BugInstance>> instances;

  public BugPattern(final String type, final String category) {
    this.type = type;
    this.category = category;
    this.instances = new HashMap<Object, List<BugInstance>>();
  }

  public void addBugInstance(final Object version, final BugInstance instance) {
    List<BugInstance> bugs = this.instances.get(version);
    if (null == bugs) {
      bugs = new ArrayList<BugInstance>();
      this.instances.put(version, bugs);
    }
    bugs.add(instance);
  }

  public SortedSet<BugInstance> getBugInstances(final Object version) {
    final SortedSet<BugInstance> instances = new TreeSet<>();
    if (this.instances.containsKey(version)) {
      instances.addAll(this.instances.get(version));
    }
    return instances;
  }

  public Float getAverageRank() {
    int sum = 0;
    final List<Integer> ranks = this.getRanks();
    for (final Integer value : ranks) {
      sum += value.intValue();
    }
    return Float.valueOf((float) sum / (float) ranks.size());
  }

  public Float getAveragePriority() {
    int sum = 0;
    final List<Integer> priorities = this.getPriorities();
    for (final Integer value : priorities) {
      sum += value.intValue();
    }
    return Float.valueOf((float) sum / (float) priorities.size());
  }

  public String getRankText() {
    final List<Integer> ranks = this.getRanks();
    Collections.sort(ranks);
    final int firstValue = ranks.get(0);
    final int lastValue = ranks.get(ranks.size() - 1);
    if (firstValue == lastValue) {
      return Integer.toString(firstValue);
    } else {
      final StringBuilder text = new StringBuilder();
      text.append(this.getAverageRank()
          .toString());
      text.append(" ( from ");
      text.append(Integer.toString(firstValue));
      text.append(" to ");
      text.append(Integer.toString(lastValue));
      text.append(")");

      return text.toString();
    }
  }

  public String getPriorityText() {
    final List<Integer> priorities = this.getPriorities();
    Collections.sort(priorities);
    final int firstValue = priorities.get(0);
    final int lastValue = priorities.get(priorities.size() - 1);
    if (firstValue == lastValue) {
      return Integer.toString(firstValue);
    } else {
      final StringBuilder text = new StringBuilder();
      text.append(this.getAveragePriority()
          .toString());
      text.append(" ( from ");
      text.append(Integer.toString(firstValue));
      text.append(" to ");
      text.append(Integer.toString(lastValue));
      text.append(" )");

      return text.toString();
    }
  }

  public List<Integer> getRanks() {
    final List<Integer> ranks = new ArrayList<Integer>();
    for (final List<BugInstance> instances : this.instances.values()) {
      for (final BugInstance instance : instances) {
        ranks.add(instance.rank);
      }
    }
    return ranks;
  }

  public List<Integer> getPriorities() {
    final List<Integer> priorities = new ArrayList<Integer>();
    for (final List<BugInstance> instances : this.instances.values()) {
      for (final BugInstance instance : instances) {
        priorities.add(instance.priority);
      }
    }
    return priorities;
  }

  @Override
  public int compareTo(final BugPattern target) {

    final int rankComparison = this.getAverageRank()
        .compareTo(target.getAverageRank());
    if (0 != rankComparison) {
      return rankComparison;
    }

    final int priorityComparison = this.getAveragePriority()
        .compareTo(target.getAveragePriority());
    if (0 != priorityComparison) {
      return priorityComparison;
    }

    final int categoryComparison = this.category.compareTo(target.category);
    if (0 != categoryComparison) {
      return categoryComparison;
    }

    final int typeComparison = this.type.compareTo(target.type);
    return typeComparison;
  }

  @Override
  public String toString() {
    final StringBuilder text = new StringBuilder();
    text.append("[BugPattern] type: ");
    text.append(this.type);
    text.append(", category: ");
    text.append(this.category);
    return text.toString();
  }
}
