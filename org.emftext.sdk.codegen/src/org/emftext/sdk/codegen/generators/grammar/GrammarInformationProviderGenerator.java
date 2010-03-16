package org.emftext.sdk.codegen.generators.grammar;

import static org.emftext.sdk.codegen.generators.IClassNameConstants.ECORE_FACTORY;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.E_CLASS;
import static org.emftext.sdk.codegen.generators.IClassNameConstants.E_STRUCTURAL_FEATURE;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenFeature;
import org.eclipse.emf.ecore.EObject;
import org.emftext.sdk.codegen.EArtifact;
import org.emftext.sdk.codegen.GenerationContext;
import org.emftext.sdk.codegen.GeneratorUtil;
import org.emftext.sdk.codegen.IGenerator;
import org.emftext.sdk.codegen.composites.StringComposite;
import org.emftext.sdk.codegen.generators.JavaBaseGenerator;
import org.emftext.sdk.codegen.util.ConcreteSyntaxUtil;
import org.emftext.sdk.concretesyntax.Annotation;
import org.emftext.sdk.concretesyntax.Cardinality;
import org.emftext.sdk.concretesyntax.CardinalityDefinition;
import org.emftext.sdk.concretesyntax.Choice;
import org.emftext.sdk.concretesyntax.CompoundDefinition;
import org.emftext.sdk.concretesyntax.ConcreteSyntax;
import org.emftext.sdk.concretesyntax.Containment;
import org.emftext.sdk.concretesyntax.CsString;
import org.emftext.sdk.concretesyntax.Definition;
import org.emftext.sdk.concretesyntax.LineBreak;
import org.emftext.sdk.concretesyntax.PLUS;
import org.emftext.sdk.concretesyntax.Placeholder;
import org.emftext.sdk.concretesyntax.QUESTIONMARK;
import org.emftext.sdk.concretesyntax.Rule;
import org.emftext.sdk.concretesyntax.STAR;
import org.emftext.sdk.concretesyntax.Sequence;
import org.emftext.sdk.concretesyntax.WhiteSpaces;
import org.emftext.sdk.util.StringUtil;

/**
 * A generator that creates a class that contains the syntax structure given
 * in the CS specification.
 */
public class GrammarInformationProviderGenerator extends JavaBaseGenerator {

	private static final String ANONYMOUS_FEATURE = "ANONYMOUS_FEATURE";

	private final static GeneratorUtil generatorUtil = new GeneratorUtil();

	private ConcreteSyntax concreteSyntax;

	private String syntaxElementClassName;
	private String keywordClassName;
	private String placeholderClassName;
	private String cardinalityClassName;
	private String compoundClassName;
	private String choiceClassName;
	private String sequenceClassName;
	private String containmentClassName;
	private String lineBreakClassName;
	private String whiteSpaceClassName;

	private static ConcreteSyntaxUtil csUtil = new ConcreteSyntaxUtil();

	public GrammarInformationProviderGenerator() {
		super();
	}

	private GrammarInformationProviderGenerator(GenerationContext context) {
		super(context, EArtifact.GRAMMAR_INFORMATION_PROVIDER);
		concreteSyntax = context.getConcreteSyntax();
		syntaxElementClassName = context.getQualifiedClassName(EArtifact.SYNTAX_ELEMENT);
		keywordClassName = context.getQualifiedClassName(EArtifact.KEYWORD);
		placeholderClassName = context.getQualifiedClassName(EArtifact.PLACEHOLDER);
		cardinalityClassName = context.getQualifiedClassName(EArtifact.CARDINALITY);
		compoundClassName = context.getQualifiedClassName(EArtifact.COMPOUND);
		choiceClassName = context.getQualifiedClassName(EArtifact.CHOICE);
		sequenceClassName = context.getQualifiedClassName(EArtifact.SEQUENCE);
		containmentClassName = context.getQualifiedClassName(EArtifact.CONTAINMENT);
		lineBreakClassName = context.getQualifiedClassName(EArtifact.LINE_BREAK);
		whiteSpaceClassName = context.getQualifiedClassName(EArtifact.WHITE_SPACE);
	}

	@Override
	public boolean generateJavaContents(StringComposite sc) {
		sc.add("package " + getResourcePackageName() + ";");
		sc.addLineBreak();
		sc.add("public class " + getResourceClassName() + " {");
		sc.addLineBreak();
		addStaticConstants(sc);
		addInnerClasses(sc);
		addConstantsForSyntaxElements(sc);
		
		sc.add("}");
		
		return true;
	}

	private void addConstantsForSyntaxElements(StringComposite sc) {
		Map<EObject, String> objectToFieldNameMap = new LinkedHashMap<EObject, String>();
		List<Rule> allRules = concreteSyntax.getAllRules();
		for (Rule rule : allRules) {
			addConstants(sc, objectToFieldNameMap, rule);
		}
	}

	private void addStaticConstants(StringComposite sc) {
		sc.add("public final static " + E_STRUCTURAL_FEATURE + " " + ANONYMOUS_FEATURE + " = " + ECORE_FACTORY + ".eINSTANCE.createEAttribute();");
		sc.add("static {");
		sc.add("ANONYMOUS_FEATURE.setName(\"_\");");
		sc.add("}");
		sc.addLineBreak();
	}

	private void addInnerClasses(StringComposite sc) {
		addRuleClass(sc);
	}

	private void addRuleClass(StringComposite sc) {
		sc.add("public static class Rule extends " + syntaxElementClassName + " {");
		sc.addLineBreak();
		sc.add("private final " + E_CLASS + " metaclass;"); 
		sc.addLineBreak();
		sc.add("public Rule(" + E_CLASS + " metaclass, " + choiceClassName + " choice, " + cardinalityClassName + " cardinality) {");
		sc.add("super(cardinality, new " + syntaxElementClassName + "[] {choice});"); 
		sc.add("this.metaclass = metaclass;");
		sc.add("}"); 
		sc.addLineBreak();
		sc.add("public " + E_CLASS + " getMetaclass() {"); 
		sc.add("return metaclass;"); 
		sc.add("}"); 
		sc.addLineBreak();
		sc.add("public " + choiceClassName + " getDefinition() {"); 
		sc.add("return (" + choiceClassName + ") getChildren()[0];"); 
		sc.add("}"); 
		sc.add("}"); 
		sc.addLineBreak();
	}

	private void addConstants(StringComposite sc, Map<EObject, String> objectToFieldNameMap, Rule rule) {
		addConstant(sc, objectToFieldNameMap, rule, rule.getDefinition());
		addConstant(sc, objectToFieldNameMap, rule, rule);
	}

	private void addConstant(StringComposite sc, Map<EObject, String> objectToFieldNameMap, Rule rule, EObject next) {
		if (next instanceof CsString) {
			String value = ((CsString) next).getValue();
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + keywordClassName + " " + fieldName + " = new " + keywordClassName + "(\"" + StringUtil.escapeToJavaString(value) + "\", " + getCardinality(next) + ");");
		} else if (next instanceof Placeholder) {
			Placeholder placeholder = (Placeholder) next;
			GenFeature feature = placeholder.getFeature();
			String getFeatureAccessor = getFeatureAccessor(rule.getMetaclass(), feature);
			String featureAccessor = getFeatureAccessor;
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + placeholderClassName + " " + fieldName + " = new " + placeholderClassName + "(" + featureAccessor + ", \"" + StringUtil.escapeToJavaString(placeholder.getToken().getName()) + "\", " + getCardinality(next) + ");");
		} else if (next instanceof WhiteSpaces) {
			int amount = ((WhiteSpaces) next).getAmount();
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + whiteSpaceClassName + " " + fieldName + " = new " + whiteSpaceClassName + "(" + amount + ", " + getCardinality(next) + ");");
		} else if (next instanceof LineBreak) {
			int amount = ((LineBreak) next).getTab();
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + lineBreakClassName + " " + fieldName + " = new " + lineBreakClassName + "(" + getCardinality(next) + ", " + amount + ");");
		} else if (next instanceof Sequence) {
			Sequence sequence = (Sequence) next;
			List<String> elements = new ArrayList<String>();
			List<Definition> parts = sequence.getParts();
			for (Definition part : parts) {
				addConstant(sc, objectToFieldNameMap, rule, part);
				elements.add(csUtil.getFieldName(part));
			}
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + sequenceClassName + " " + fieldName + " = new " + sequenceClassName + "(" + getCardinality(next) + ", " + StringUtil.explode(elements, ", ") + ");");
		} else if (next instanceof Choice) {
			Choice choice = (Choice) next;
			List<String> elements = new ArrayList<String>();
			List<Sequence> parts = choice.getOptions();
			for (Sequence part : parts) {
				addConstant(sc, objectToFieldNameMap, rule, part);
				elements.add(csUtil.getFieldName(part));
			}
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + choiceClassName + " " + fieldName + " = new " + choiceClassName + "(" + getCardinality(next) + ", " + StringUtil.explode(elements, ", ") + ");");
		} else if (next instanceof Containment) {
			Containment containment = (Containment) next;
			GenFeature feature = containment.getFeature();
			String featureAccessor = getFeatureAccessor(rule.getMetaclass(), feature);
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + containmentClassName + " " + fieldName + " = new " + containmentClassName + "(" + featureAccessor + ", " + getCardinality(next) + ");");
		} else if (next instanceof CompoundDefinition) {
			CompoundDefinition compound = (CompoundDefinition) next;
			Choice choice = compound.getDefinitions();
			addConstant(sc, objectToFieldNameMap, rule, choice);
			String choiceFieldName = csUtil.getFieldName(choice);
			String fieldName = csUtil.getFieldName(next);
			sc.add("public final static " + compoundClassName + " " + fieldName + " = new " + compoundClassName + "(" + choiceFieldName + ", " + getCardinality(next) + ");");
		} else if (next instanceof Rule) {
			Rule nextAsRule = (Rule) next;
			String definitionFieldName = csUtil.getFieldName(nextAsRule.getDefinition());
			String metaClassAccessor = generatorUtil.getClassAccessor(nextAsRule.getMetaclass());
			String fieldName = csUtil.getFieldName(nextAsRule);
			sc.add("public final static Rule " + fieldName + " = new Rule(" + metaClassAccessor + ", " + definitionFieldName + ", " + getCardinality(next) + ");");
		} else {
			assert next instanceof Annotation;
		}
	}

	private String getCardinality(EObject next) {
		String literal = "ONE";
		if (next instanceof CardinalityDefinition) {
			CardinalityDefinition cd = (CardinalityDefinition) next;
			Cardinality cardinality = cd.getCardinality();
			if (cardinality != null) {
				if (cardinality instanceof STAR) {
					literal = "STAR";
				} else if (cardinality instanceof PLUS) {
					literal = "PLUS";
				} else if (cardinality instanceof QUESTIONMARK) {
					literal = "QUESTIONMARK";
				}
			}
		}
		return cardinalityClassName + "." + literal;
	}

	private String getFeatureAccessor(GenClass genClass, GenFeature genFeature) {
		if (genFeature == ConcreteSyntaxUtil.ANONYMOUS_GEN_FEATURE) {
			return ANONYMOUS_FEATURE;
		} else {
			return generatorUtil.getFeatureAccessor(genClass, genFeature);
		}
	}

	public IGenerator newInstance(GenerationContext context) {
		return new GrammarInformationProviderGenerator(context);
	}
}