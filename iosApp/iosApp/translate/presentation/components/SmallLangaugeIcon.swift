//
//  SmallLangaugeIcon.swift
//  iosApp
//
//  Created by Anjali Prajapati on 09/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SmallLangaugeIcon: View {
    
    var langauge:UiLanguage
    
    var body: some View {
        Image(uiImage:UIImage(named: langauge.imageName.lowercased())!)
            .resizable()
            .frame(width: 30,height: 30)
    }
}

#Preview {
    SmallLangaugeIcon(langauge: UiLanguage(language: .english, imageName:"english"))
}
