//
//  IOSVoiceToTextParser.swift
//  iosApp
//
//  Created by Anjali Prajapati on 17/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import Speech
import Combine

class IOSVoiceToTextParser : VoiceToTextParser {
    
    private let _state = IOSMutableStateFlow(initialValue: VoiceToTextParserState()
    
    var state: CommonStateFlow<VoiceToTextParserState>
    
    func cancel() {
        <#code#>
    }
    
    func reset() {
        <#code#>
    }
    
    func startListening(languageCode: String) {
        <#code#>
    }
    
    func stopListening() {
        <#code#>
    }
    
   
    
    
    
    
}
