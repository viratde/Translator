//
//  LanguageDisplay.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct LanguageDisplay: View {
    
    var language: UiLanguage
    
    var body: some View {
        HStack{
            SmallLangaugeIcon(langauge: language)
                .padding(.trailing,5)
            Text(language.language.langName)
                .foregroundStyle(Color.lightBlue)
            
        }
    }
}

#Preview {
    LanguageDisplay(
        language: UiLanguage(language: .english, imageName: "english")
    )
}
