package org.emftext.runtime.resource.impl.code_completion;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.emftext.runtime.resource.IExpectedElement;
import org.emftext.runtime.resource.IReferenceMapping;
import org.emftext.runtime.resource.IReferenceResolveResult;
import org.emftext.runtime.resource.IReferenceResolverSwitch;
import org.emftext.runtime.resource.ITextParser;
import org.emftext.runtime.resource.ITextResourcePluginMetaInformation;
import org.emftext.runtime.resource.ITokenResolver;
import org.emftext.runtime.resource.ITokenResolverFactory;
import org.emftext.runtime.resource.impl.ReferenceResolveResult;
import org.emftext.runtime.util.EClassUtil;
import org.emftext.runtime.util.StringUtil;

/**
 * A CodeCompletionHelper can be used to derive completion proposals for partial 
 * documents. It run the parser generated by EMFText in a special mode (i.e., the
 * rememberExpectedElements mode). Based on the elements that are expected by the
 * parser for different regions in the document, valid proposals are computed. 
 */
// TODO mseifert: calculate and consider the prefix and return only the proposals
// that start with the prefix
public class CodeCompletionHelper {

	private final static EClassUtil eClassUtil = new EClassUtil();

	/**
	 * Computes a set of proposals for the given document assuming the cursor is 
	 * at 'cursorOffset'. The proposals are derived using the meta information, i.e.,
	 * the generated language plug-in.
	 * 
	 * @param metaInformation
	 * @param content the documents content
	 * @param cursorOffset
	 * @return
	 */
	public Collection<String> computeCompletionProposals(ITextResourcePluginMetaInformation metaInformation, String content, int cursorOffset) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(content.getBytes());
		ITextParser parser = metaInformation.createParser(inputStream, null);
		final List<IExpectedElement> expectedElements = parseToExpectedElements(parser);
		if (expectedElements == null) {
			return Collections.emptyList();
		}
		if (expectedElements.size() == 0) {
			return Collections.emptyList();
		}
		List<IExpectedElement> expectedElementsAt = getExpectedElements(expectedElements, cursorOffset);
		setPrefix(expectedElementsAt, content, cursorOffset);
		// TODO this is done twice (was already calculated in getFinalExpectedElementAt())
		//IExpectedElement expectedAtCursor = getExpectedElementAt(offset, expectedElements);
		System.out.println("Parser returned expectation: " + expectedElementsAt + " for offset " + cursorOffset);
		Collection<String> proposals = deriveProposals(expectedElementsAt, content, metaInformation, cursorOffset);
		
		/*Collection<String> filteredProposals = filter(document, offset,
				expectedElementsAt, expectedAtCursor, proposals);*/
		
		final List<String> sortedProposals = new ArrayList<String>(proposals);
		Collections.sort(sortedProposals);
		return sortedProposals;
	}
	
	public List<IExpectedElement> parseToExpectedElements(ITextParser parser) {
		final List<IExpectedElement> expectedElements = parser.parseToExpectedElements(null);
		if (expectedElements == null) {
			return Collections.emptyList();
		}
		removeDuplicateEntries(expectedElements);
		removeInvalidEntriesAtEnd(expectedElements);
		for (IExpectedElement expectedElement : expectedElements) {
			System.out.println("PARSER EXPECTS:   " + expectedElement);
		}
		return expectedElements;
	}

	private void removeDuplicateEntries(List<IExpectedElement> expectedElements) {
		for (int i = 0; i < expectedElements.size() - 1; i++) {
			IExpectedElement elementAtIndex = expectedElements.get(i);
			for (int j = i + 1; j < expectedElements.size();) {
				IExpectedElement elementAtNext = expectedElements.get(j);
				if (elementAtIndex.equals(elementAtNext) &&
					elementAtIndex.getStartExcludingHiddenTokens() == elementAtNext.getStartExcludingHiddenTokens()) {
					expectedElements.remove(j);
				} else {
					j++;
				}
			}
		}
	}

	private void removeInvalidEntriesAtEnd(List<IExpectedElement> expectedElements) {
		for (int i = 0; i < expectedElements.size() - 1;) {
			IExpectedElement elementAtIndex = expectedElements.get(i);
			IExpectedElement elementAtNext = expectedElements.get(i + 1);
			if (elementAtIndex.getStartExcludingHiddenTokens() == elementAtNext.getStartExcludingHiddenTokens() &&
				elementAtIndex.discardFollowingExpectations() &&
				// TODO mseifert: this is wrong. we must compare the scopeIDs based on their parts!
				elementAtIndex.getScopeID().startsWith(elementAtNext.getScopeID())) {
				expectedElements.remove(i + 1);
			} else {
				i++;
			}
		}
	}

/*
	private Collection<String> filter(String document, int offset,
			final List<IExpectedElement> expectedElements,
			IExpectedElement expectedAtCursor, Collection<String> proposals) {
		Collection<String> filteredProposals = new ArrayList<String>();

		//String prefix = findPrefix(expectedElements, expectedAtCursor, document, offset);
		System.out.println("computeCompletionProposals() PREFIX = " + prefix);
		for (String proposal : proposals) {
			if (proposal.startsWith(prefix)) {
				filteredProposals.add(proposal);
			}
		}
		return filteredProposals;
	}
*/
	private String findPrefix(List<IExpectedElement> expectedElements, IExpectedElement expectedAtCursor, String content, int cursorOffset) {
		if (cursorOffset < 0) {
			return "";
		}
		int end = 0;
		for (IExpectedElement expectedElement : expectedElements) {
			if (expectedElement == expectedAtCursor) {
				final int start = expectedElement.getStartExcludingHiddenTokens();
				if (start >= 0  && start < Integer.MAX_VALUE) {
					end = start;
				}
				break;
			}
			// TODO fix calculation of 'end'
			/*
			final int endIncludingHiddenTokens = expectedElement.getEndIncludingHiddenTokens();
			if (endIncludingHiddenTokens >= 0 && endIncludingHiddenTokens < Integer.MAX_VALUE) {
				end = endIncludingHiddenTokens;
			}
			*/
			// TODO trim?
			/*
			int endExcludingHidden = expectedElement.getEndExcludingHiddenTokens();
			if (endExcludingHidden >= 0) {
				end = endExcludingHidden;
			}
			if (end >= offset) {
				end = offset;
				break;
			}
			*/
		}
		end = Math.min(end, cursorOffset);
		//System.out.println("substring("+end+","+offset+")");
		final String prefix = content.substring(end, Math.min(content.length(), cursorOffset + 1));
		System.out.println("Found prefix '" + prefix + "'");
		return prefix;
	}

	private Collection<String> deriveProposals(
			List<IExpectedElement> expectedElements, String content, ITextResourcePluginMetaInformation metaInformation, int cursorOffset) {
		Collection<String> resultSet = new HashSet<String>();
		for (IExpectedElement expectedElement : expectedElements) {
			resultSet.addAll(deriveProposals(expectedElement, content, metaInformation, cursorOffset));
		}
		return resultSet;
	}

	private Collection<String> deriveProposals(
			IExpectedElement expectedElement, String content, ITextResourcePluginMetaInformation metaInformation, int cursorOffset) {
		if (expectedElement instanceof ExpectedCsString) {
			ExpectedCsString csString = (ExpectedCsString) expectedElement;
			return deriveProposal(csString, content, cursorOffset);
		} else if (expectedElement instanceof ExpectedStructuralFeature) {
			ExpectedStructuralFeature expectedFeature = (ExpectedStructuralFeature) expectedElement;
			EStructuralFeature feature = expectedFeature.getFeature();
			EClassifier featureType = feature.getEType();
			EObject container = expectedFeature.getContainer();
			if (feature instanceof EReference) {
				EReference reference = (EReference) feature;
				if (featureType instanceof EClass) {
					if (reference.isContainment()) {
						EClass classType = (EClass) featureType;
						return deriveProposals(classType, metaInformation, content, cursorOffset);
					} else {
						return handleNCReference(content, metaInformation, cursorOffset, container);
					}
				}
			} else if (feature instanceof EAttribute) {
				EAttribute attribute = (EAttribute) feature;
				if (featureType instanceof EEnum) {
					EEnum enumType = (EEnum) featureType;
					return deriveProposals(expectedElement, enumType, content, cursorOffset);
				} else {
					// handle EAttributes (derive default value depending on
					// the type of the attribute, figure out token resolver, and
					// call deResolve())
					return handleAttribute(metaInformation, expectedFeature, container, attribute);
				}
			} else {
				// there should be no other subclass of EStructuralFeature
				assert false;
			}
		} else {
			// there should be no other class implementing IExpectedElement
			assert false;
		}
		return Collections.emptyList();
	}

	private Collection<String> handleNCReference(String content,
			ITextResourcePluginMetaInformation metaInformation, int cursorOffset,
			EObject container) {
		// handle non-containment references
		IReferenceResolverSwitch resolverSwitch = metaInformation.getReferenceResolverSwitch();
		IReferenceResolveResult<EObject> result = new ReferenceResolveResult<EObject>(true);
		resolverSwitch.resolveFuzzy("", container, 0, result);
		Collection<IReferenceMapping<EObject>> mappings = result.getMappings();
		if (mappings != null) {
			Collection<String> resultSet = new HashSet<String>();
			for (IReferenceMapping<EObject> mapping : mappings) {
				final String identifier = mapping.getIdentifier();
				System.out.println("deriveProposals() " + identifier);
				resultSet.add(identifier);
			}
			return resultSet;
		}
		return Collections.emptyList();
	}

	private Collection<String> handleAttribute(
			ITextResourcePluginMetaInformation metaInformation,
			ExpectedStructuralFeature expectedFeature, EObject container,
			EAttribute attribute) {
		Object defaultValue = getDefaultValue(attribute);
		if (defaultValue != null) {
			ITokenResolverFactory tokenResolverFactory = metaInformation.getTokenResolverFactory();
			String tokenName = expectedFeature.getTokenName();
			if (tokenName != null) {
				ITokenResolver tokenResolver = tokenResolverFactory.createTokenResolver(tokenName);
				if (tokenResolver != null) {
					String defaultValueAsString = tokenResolver.deResolve(defaultValue, attribute, container);
					Collection<String> resultSet = new HashSet<String>();
					resultSet.add(defaultValueAsString);
					return resultSet;
				}
			}
		}
		return Collections.emptyList();
	}

	private Object getDefaultValue(EAttribute attribute) {
		String typeName = attribute.getEType().getName();
		if ("EString".equals(typeName)) {
			return "some" + StringUtil.capitalize(attribute.getName());
		}
		// TODO mseifert: add more default values for other types
		System.out.println("CodeCompletionHelper.getDefaultValue() unknown type " + typeName);
		return attribute.getDefaultValue();
	}

	private Collection<String> deriveProposals(
			EClass type,
			ITextResourcePluginMetaInformation metaInformation, 
			String content, int cursorOffset) {
		Collection<String> allProposals = new HashSet<String>();
		// find all sub classes and call parseToExpectedElements() for each
		// of them
		EClass[] availableClasses = metaInformation.getClassesWithSyntax();
		Collection<EClass> allSubClasses = eClassUtil.getSubClasses(type, availableClasses);
		for (EClass subClass : allSubClasses) {
			ITextParser parser = metaInformation.createParser(new ByteArrayInputStream(new byte[0]), null);
			final List<IExpectedElement> expectedElements = parser.parseToExpectedElements(subClass);
			if (expectedElements == null) {
				continue;
			}
			if (expectedElements.size() == 0) {
				continue;
			}
			List<IExpectedElement> expectedElementsAt = getExpectedElements(expectedElements, 0);
			setPrefix(expectedElementsAt, content, 0);
			System.out.println("computeCompletionProposals() " + expectedElementsAt + " for offset " + cursorOffset);
			Collection<String> proposals = deriveProposals(expectedElementsAt, content, metaInformation, cursorOffset);
			allProposals.addAll(proposals);
		}
		return allProposals;
	}

	private Collection<String> deriveProposals(IExpectedElement expectedElement, EEnum enumType, String content, int cursorOffset) {
		Collection<EEnumLiteral> enumLiterals = enumType.getELiterals();
		Collection<String> result = new HashSet<String>();
		for (EEnumLiteral literal : enumLiterals) {
			String proposal = literal.getLiteral();
			if (proposal.startsWith(expectedElement.getPrefix())) {
				result.add(proposal);
			}
		}
		return result;
	}

	private Collection<String> deriveProposal(ExpectedCsString csString,
			String content, int cursorOffset) {
		String proposal = csString.getValue();
		
		Collection<String> result = new HashSet<String>(1);
		result.add(proposal);
		return result;
	}
	
	/**
	 * Returns the element(s) that is most suitable at the given cursor
	 * index based on the list of expected elements.
	 * 
	 * @param cursorOffset
	 * @param allExpectedElements
	 * @return
	 */
	// TODO mseifert: figure out what other combinations of elements before
	// and after the cursor position exist and which action should be taken.
	// For example, when a StructuralFeature is expected right before the
	// cursor and a CsString right after, we should return both elements.
	public List<IExpectedElement> getExpectedElements(final List<IExpectedElement> allExpectedElements,
			int cursorOffset) {

		List<IExpectedElement> expectedAfterCursor = getElementsExpectedAt(allExpectedElements, cursorOffset);
		List<IExpectedElement> expectedBeforeCursor = getElementsExpectedAt(allExpectedElements, cursorOffset - 1);
		System.out.println("parseToCursor(" + cursorOffset + ") BEFORE CURSOR " + expectedBeforeCursor);
		System.out.println("parseToCursor(" + cursorOffset + ") AFTER CURSOR  " + expectedAfterCursor);
		List<IExpectedElement> allExpectedAtCursor = new ArrayList<IExpectedElement>();
		allExpectedAtCursor.addAll(expectedAfterCursor);
		if (expectedBeforeCursor != null) {
			for (IExpectedElement expectedBefore : expectedBeforeCursor) {
				// if the thing right before the cursor is something that could
				// be long we add it to the list of proposals
				if (expectedBefore instanceof ExpectedStructuralFeature) {
					//allExpectedAtCursor.clear();
					allExpectedAtCursor.add(expectedBefore);
				}
			}
		}
		return allExpectedAtCursor;
	}

	private void setPrefix(List<IExpectedElement> allExpectedElements, String content, int cursorOffset) {
		if (cursorOffset < 0) {
			return;
		}
		for (IExpectedElement expectedElementAtCursor : allExpectedElements) {
			expectedElementAtCursor.setPrefix(findPrefix(allExpectedElements, expectedElementAtCursor, content, cursorOffset));
		}
	}

	public List<IExpectedElement> getElementsExpectedAt(List<IExpectedElement> allExpectedElements, int cursorOffset) {
		List<IExpectedElement> expectedAtCursor = new ArrayList<IExpectedElement>();
		for (int i = 0; i < allExpectedElements.size(); i++) {
			IExpectedElement expectedElement = allExpectedElements.get(i);
			
			int startIncludingHidden = expectedElement.getStartIncludingHiddenTokens();
			//int startExcludingHidden = expectedElement.getStartExcludingHiddenTokens();
			int end = getEnd(allExpectedElements, i);
			//System.out.println("END = " + end + " for " + expectedElement);
			if (cursorOffset >= startIncludingHidden &&
				cursorOffset <= end) {
				expectedAtCursor.add(expectedElement);
			}
		}
		return expectedAtCursor;
	}

	private int getEnd(List<IExpectedElement> allExpectedElements, int indexInList) {
		IExpectedElement elementAtIndex = allExpectedElements.get(indexInList);
		int startIncludingHidden = elementAtIndex.getStartIncludingHiddenTokens();
		int startExcludingHidden = elementAtIndex.getStartExcludingHiddenTokens();
		
		for (int i = indexInList + 1; i < allExpectedElements.size(); i++) {
			IExpectedElement elementAtI = allExpectedElements.get(i);
			int startIncludingHiddenForI = elementAtI.getStartIncludingHiddenTokens();
			int startExcludingHiddenForI = elementAtI.getStartExcludingHiddenTokens();
			if (startIncludingHidden != startIncludingHiddenForI || startExcludingHidden != startExcludingHiddenForI) {
				return startIncludingHiddenForI - 1;
			}
		}
		return Integer.MAX_VALUE;
	}
}
